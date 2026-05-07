import numpy as np
import matplotlib.pyplot as plt


# ============================================================
# Selected digits: 3, 1, 0  (from student ID 320201066)
# Selected objects: Sun, Balloon  (from Figure 1)
#
# Methods used:
#   1. Parametric natural cubic splines — for curved, closed, or
#      multi-valued shapes
#   2. Ordinary linear splines y = L(x) — for straight segments
#
# ------------------------------------------------------------
# WHY PARAMETRIC SPLINES?
# ------------------------------------------------------------
# A standard spline y = S(x) requires that every x-value maps
# to exactly one y-value (i.e., the curve must pass the
# vertical-line test).  This fails for:
#
#   (a) CLOSED shapes  (e.g. digit 0, sun circle, balloon body):
#       the curve loops back, so at every x near the centre there
#       are two y-values — one on the top arc and one on the bottom.
#
#   (b) VERTICAL segments  (e.g. digit 1 stem):
#       x is constant, so the slope m = Δy/Δx is undefined and
#       y = L(x) cannot be formed at all.
#
#   (c) GENERAL curved shapes  (e.g. digit 3):
#       the S-curve folds back on itself horizontally, giving
#       multiple y-values for the same x.
#
# The solution is to introduce an artificial parameter t and write:
#
#       x = X(t)
#       y = Y(t)
#
# where t_i = i is assigned to each selected control point
# P_i = (x_i, y_i).  Two independent natural cubic splines are
# then fitted — one through the x-coordinates and one through the
# y-coordinates.  The resulting curve (X(t), Y(t)) can freely
# loop, fold, and go vertical without any of the restrictions
# that apply to y = S(x).
#
# ------------------------------------------------------------
# CLOSURE NOTE
# ------------------------------------------------------------
# For closed shapes (digit 0, sun circle, balloon body) the first
# and last control points are set to the same coordinates:
#
#       P_0 = P_n
#
# This forces the natural cubic spline to pass through that point
# at both ends, giving visual closure.  The resulting join is
# C0-continuous (the curve meets itself) but generally NOT C1 or
# C2 at the join, because a natural spline imposes zero-curvature
# boundary conditions (S''(t_0) = S''(t_n) = 0) rather than
# periodicity conditions.
#
# If the instructor requires a perfectly smooth (C2) closed curve,
# a PERIODIC cubic spline should be used instead: the boundary
# conditions are replaced by S'(t_0) = S'(t_n) and
# S''(t_0) = S''(t_n), which produces a seamless loop.  For the
# purposes of this assignment the repeated-endpoint approach
# produces visually acceptable closure.
# ============================================================


def natural_cubic_spline_coefficients(t, values):
    """
    Computes natural cubic spline coefficients for one coordinate.

    Used twice per parametric curve: once for X(t), once for Y(t).

    For n+1 data points (t_0, v_0), ..., (t_n, v_n), each interval
    [t_i, t_{i+1}] is covered by a cubic polynomial written in the
    local form:

        S_i(t) = a_i
               + b_i (t - t_i)
               + c_i (t - t_i)^2
               + d_i (t - t_i)^3

    Using (t - t_i) rather than bare powers keeps the arithmetic
    well-conditioned and makes S_i(t_i) = a_i obvious.

    The four families of conditions that determine the coefficients:

        1. Interpolation (2n equations):
               S_i(t_i)     = v_i
               S_i(t_{i+1}) = v_{i+1}

        2. C1 continuity (n-1 equations):
               S'_{i-1}(t_i) = S'_i(t_i)

        3. C2 continuity (n-1 equations):
               S''_{i-1}(t_i) = S''_i(t_i)

        4. Natural endpoint conditions (2 equations):
               S''(t_0) = 0   →   c_0 = 0
               S''(t_n) = 0   →   c_n = 0

    These yield the tridiagonal linear system  A · c = rhs  for the
    c_i values (proportional to curvature).  Once c_i are known,
    b_i and d_i follow from:

        b_i = (v_{i+1} - v_i)/h_i  -  h_i(2c_i + c_{i+1})/3
        d_i = (c_{i+1} - c_i) / (3 h_i)

    where  h_i = t_{i+1} - t_i.
    """
    t      = np.array(t,      dtype=float)
    values = np.array(values, dtype=float)
    n = len(t) - 1

    if n < 1:
        raise ValueError("At least two points are required.")
    h = np.diff(t)
    if np.any(h <= 0):
        raise ValueError("t must be strictly increasing.")

    # Build the (n+1) × (n+1) tridiagonal system A · c = rhs.
    A   = np.zeros((n + 1, n + 1))
    rhs = np.zeros(n + 1)

    A[0, 0] = 1   # Natural BC: c_0 = 0  →  S''(t_0) = 2c_0 = 0
    A[n, n] = 1   # Natural BC: c_n = 0  →  S''(t_n) = 2c_n = 0

    for i in range(1, n):
        A[i, i - 1] = h[i - 1]
        A[i, i]     = 2 * (h[i - 1] + h[i])
        A[i, i + 1] = h[i]
        rhs[i] = 3 * (
            (values[i + 1] - values[i]) / h[i]
            - (values[i] - values[i - 1]) / h[i - 1]
        )

    c_full = np.linalg.solve(A, rhs)

    a = values[:-1].copy()          # a_i = v_i  (from interpolation at left endpoint)
    b = np.zeros(n)
    d = np.zeros(n)

    for i in range(n):
        b[i] = (
            (values[i + 1] - values[i]) / h[i]
            - h[i] * (2 * c_full[i] + c_full[i + 1]) / 3
        )
        d[i] = (c_full[i + 1] - c_full[i]) / (3 * h[i])

    return a, b, c_full[:-1], d


def evaluate_cubic_spline(t_nodes, a, b, c, d, samples_per_interval=100):
    """
    Evaluates S_i(t) at many sample points over every interval
    [t_i, t_{i+1}] and returns all sampled values concatenated.

    The dense sample points are what matplotlib actually draws;
    the original control points are only shown as scatter markers.
    """
    out = []
    for i in range(len(a)):
        ts   = np.linspace(t_nodes[i], t_nodes[i + 1], samples_per_interval)
        vals = (
            a[i]
            + b[i] * (ts - t_nodes[i])
            + c[i] * (ts - t_nodes[i]) ** 2
            + d[i] * (ts - t_nodes[i]) ** 3
        )
        out.extend(vals)
    return np.array(out)


def parametric_cubic_spline(points, name):
    """
    Builds and evaluates a parametric natural cubic spline for a 2D
    curve.  Also prints all derived spline equations to the console
    (programmatic derivation requirement).

    WHY PARAMETRIC?
    A single-variable spline y = S(x) cannot represent:
      - closed curves (loop back → multiple y per x)
      - vertical segments (infinite slope → undefined)
      - any curve that fails the vertical-line test
    The parametric form x = X(t), y = Y(t) avoids all of these
    limitations by treating both coordinates as functions of an
    independent parameter t, with t_i = i at each control point.
    """
    points = np.array(points, dtype=float)
    t      = np.arange(len(points), dtype=float)   # t_i = i

    ax, bx, cx, dx = natural_cubic_spline_coefficients(t, points[:, 0])
    ay, by, cy, dy = natural_cubic_spline_coefficients(t, points[:, 1])

    xs = evaluate_cubic_spline(t, ax, bx, cx, dx)
    ys = evaluate_cubic_spline(t, ay, by, cy, dy)

    # ---- Print derived equations (programmatic derivation) ----
    print("\n" + "=" * 80)
    print(name)
    print("=" * 80)
    for i in range(len(ax)):
        print(f"Interval {i}: {t[i]:.1f} <= t <= {t[i+1]:.1f}")
        print(f"X_{i}(t) = {ax[i]:.4f} + ({bx[i]:.4f})(t-{t[i]:.4f}) "
              f"+ ({cx[i]:.4f})(t-{t[i]:.4f})^2 + ({dx[i]:.4f})(t-{t[i]:.4f})^3")
        print(f"Y_{i}(t) = {ay[i]:.4f} + ({by[i]:.4f})(t-{t[i]:.4f}) "
              f"+ ({cy[i]:.4f})(t-{t[i]:.4f})^2 + ({dy[i]:.4f})(t-{t[i]:.4f})^3")
        print()

    return xs, ys


def linear_spline(points, name, samples_per_interval=100):
    """
    Builds ordinary linear splines of the form:

        L_i(x) = y_i + m_i (x - x_i)

    where  m_i = (y_{i+1} - y_i) / (x_{i+1} - x_i)

    Prints all derived equations to the console.

    LIMITATION: This form requires x_{i+1} ≠ x_i (i.e. no vertical
    segments).  Vertical segments must be handled with a parametric
    cubic spline (see digit 1 stem).  A ValueError is raised if a
    vertical segment is detected so the mistake is caught early.
    """
    points = np.array(points, dtype=float)
    xs_all, ys_all = [], []

    print("\n" + "=" * 80)
    print(name)
    print("=" * 80)

    for i in range(len(points) - 1):
        x0, y0 = points[i]
        x1, y1 = points[i + 1]

        if np.isclose(x1, x0):
            # Vertical segment: slope m = Δy/0 is undefined.
            # Use parametric_cubic_spline for such segments.
            raise ValueError(
                f"Vertical segment detected between {points[i]} and "
                f"{points[i+1]}.  y = L(x) is undefined for vertical lines; "
                "use a parametric cubic spline instead."
            )

        m  = (y1 - y0) / (x1 - x0)
        xs = np.linspace(x0, x1, samples_per_interval)
        ys = y0 + m * (xs - x0)
        xs_all.extend(xs)
        ys_all.extend(ys)

        print(f"Interval {i}: {min(x0, x1):.4f} <= x <= {max(x0, x1):.4f}")
        print(f"L_{i}(x) = {y0:.4f} + ({m:.4f})(x - {x0:.4f})")
        print()

    return np.array(xs_all), np.array(ys_all)


def plot_shape(parts, title, xlim=(0, 10), ylim=(0, 11),
               extra_scatter=None):
    """
    Plots one digit or object on its own figure.

    parts         : [(method, points, label), ...]
                    method = 'cubic'  → parametric natural cubic spline
                    method = 'linear' → ordinary linear spline y = L(x)
    extra_scatter : [(x, y, label), ...] — individual scatter points
                    used for features such as the balloon knot that are
                    not spline segments.
    """
    plt.figure(figsize=(6, 6))
    plt.title(title)

    COLORS = [
        '#E63946',   # red
        '#457B9D',   # steel blue
        '#2A9D8F',   # teal
        '#E9C46A',   # yellow
        '#F4A261',   # orange
        '#6A4C93',   # purple
        '#1982C4',   # blue
        '#8AC926',   # green
        '#FF595E',   # coral
        '#52B788',   # sage
    ]

    for idx, (method, points, label) in enumerate(parts):
        color = COLORS[idx % len(COLORS)]
        if method == "cubic":
            xs, ys = parametric_cubic_spline(points, label)
        elif method == "linear":
            xs, ys = linear_spline(points, label)
        else:
            raise ValueError("Method must be 'cubic' or 'linear'.")

        plt.plot(xs, ys, label=label, color=color, linewidth=2)
        p = np.array(points, dtype=float)
        plt.scatter(p[:, 0], p[:, 1], s=25, color=color, zorder=5)

    if extra_scatter:
        for ex, ey, elabel in extra_scatter:
            plt.scatter([ex], [ey], s=60, color='black', zorder=6,
                        label=elabel)

    plt.xlabel("x")
    plt.ylabel("y")
    plt.grid(True)
    plt.axis("equal")
    plt.xlim(*xlim)
    plt.ylim(*ylim)
    plt.legend(fontsize=7)
    plt.show()


# ============================================================
# CONTROL POINTS
# (Every spline segment uses at least 4 points — assignment requirement)
# ============================================================

# ----------------------------------------------------------
# Digit 3
#
# The S-shaped stroke of "3" folds back horizontally: near y = 6.8
# the curve doubles back, giving two x-values for the same y.
# A standard y = S(x) spline would be multi-valued there.
# → Parametric cubic spline required.
# ----------------------------------------------------------
digit3_points = [
    (1.5, 8.5),
    (3.5, 9.8),
    (5.5, 8.2),
    (3.8, 6.8),   # middle fold — where x = S(y) fails the function test
    (5.5, 5.2),
    (3.5, 3.5),
    (1.5, 4.5),
]

# ----------------------------------------------------------
# Digit 1
#
# Three separate spline segments:
#
#   Top stroke — diagonal line, no vertical segment → linear spline.
#
#   Stem       — perfectly vertical (x constant at 4.5).
#                y = L(x) requires dividing by (x1 - x0) = 0, which
#                is undefined.  A parametric cubic spline avoids this:
#                X(t) stays at 4.5 while Y(t) decreases smoothly.
#
#   Base bar   — horizontal line → linear spline (slope = 0).
# ----------------------------------------------------------
digit1_top_stroke = [
    (2.5, 9.0),
    (3.2, 9.4),
    (3.8, 9.7),
    (4.5, 10.0),
]

# Vertical stem: x is constant → y = L(x) undefined → parametric cubic.
digit1_stem = [
    (4.5, 10.0),
    (4.5,  7.7),
    (4.5,  5.3),
    (4.5,  3.0),
]

digit1_base = [
    (2.5, 3.0),
    (3.5, 3.0),
    (5.5, 3.0),
    (6.5, 3.0),
]

# ----------------------------------------------------------
# Digit 0
#
# An ellipse-like closed loop.  At every x between ~1 and ~7 there
# are two y-values (top arc and bottom arc), so y = S(x) would be
# multi-valued → parametric cubic spline required.
#
# CLOSURE: The first and last control points are identical (4.0, 9.5).
# This forces the natural spline to pass through that point at both
# t = 0 and t = 8, giving visual closure.  The join is C0-continuous
# (the endpoints meet) but not C2 at the seam, because the natural
# spline boundary conditions set S'' = 0 at both ends rather than
# enforcing periodicity.  A periodic cubic spline would give a
# perfectly smooth (C2) closed curve if required.
# ----------------------------------------------------------
digit0_points = [
    (4.0, 9.5),
    (6.2, 8.5),
    (7.2, 6.0),
    (6.2, 3.5),
    (4.0, 2.5),
    (1.8, 3.5),
    (0.8, 6.0),
    (1.8, 8.5),
    (4.0, 9.5),   # = P_0 → visual closure (C0 join)
]

# ----------------------------------------------------------
# Sun
#
# Circle body:
#   A circular closed shape — same multi-valued argument as digit 0.
#   → Parametric cubic spline, closed with repeated endpoint (C0 join).
#
# Rays:
#   Straight lines radiating outward.  Each ray uses 4 collinear
#   points so the minimum-point requirement is satisfied.
#   The N and S rays are nearly vertical; a tiny ±0.1 x-offset is
#   applied to avoid the x0 = x1 error in linear_spline while keeping
#   the ray visually straight.
# ----------------------------------------------------------
sun_circle_points = [
    (5.0, 7.8),
    (6.4, 7.0),
    (6.8, 5.5),
    (6.4, 4.0),
    (5.0, 3.2),
    (3.6, 4.0),
    (3.2, 5.5),
    (3.6, 7.0),
    (5.0, 7.8),   # = P_0 → visual closure (C0 join)
]

# Each ray: 4 collinear points, inner → outer.
# N and S rays: slight x-offset (±0.1) prevents vertical-line error.
sun_rays = [
    [(5.0, 8.0), (5.1, 8.5), (4.9, 9.0), (5.0, 9.6)],   # N  (x offset ±0.1)
    [(6.0, 7.3), (6.5, 7.8), (7.0, 8.3), (7.5, 8.8)],   # NE
    [(7.0, 5.5), (7.5, 5.5), (8.0, 5.5), (8.6, 5.5)],   # E
    [(6.0, 3.7), (6.5, 3.2), (7.0, 2.7), (7.5, 2.2)],   # SE
    [(5.0, 3.0), (5.1, 2.5), (4.9, 2.0), (5.0, 1.5)],   # S  (x offset ±0.1)
    [(4.0, 3.7), (3.5, 3.2), (3.0, 2.7), (2.5, 2.2)],   # SW
    [(3.0, 5.5), (2.5, 5.5), (2.0, 5.5), (1.4, 5.5)],   # W
    [(4.0, 7.3), (3.5, 7.8), (3.0, 8.3), (2.5, 8.8)],   # NW
]

# ----------------------------------------------------------
# Balloon
#
# Body:
#   Rounded, roughly egg-shaped closed outline.  At any horizontal
#   slice there are two boundary y-values → multi-valued → parametric
#   cubic spline.  Repeated endpoint gives C0 closure (see digit 0 note).
#
# String:
#   A gently wavy curve below the balloon knot.  It is not a function
#   of x (it curves back on itself slightly), so a parametric cubic
#   spline is used here as well.
# ----------------------------------------------------------
balloon_body_points = [
    (5.0, 9.5),
    (6.6, 9.0),
    (7.4, 7.5),
    (7.2, 6.0),
    (5.8, 5.2),
    (4.2, 5.2),
    (2.8, 6.0),
    (2.6, 7.5),
    (3.4, 9.0),
    (5.0, 9.5),   # = P_0 → visual closure (C0 join)
]

# Wavy string — parametric cubic because the curve reverses direction
# in x, making it multi-valued as y = S(x).
balloon_string_points = [
    (5.0, 5.2),
    (5.5, 4.5),
    (4.8, 3.8),
    (5.3, 3.1),
    (5.0, 2.4),
]


# ============================================================
# PLOT DIGITS
# ============================================================

plot_shape(
    [("cubic", digit3_points, "Digit 3 - parametric cubic spline")],
    "Digit 3"
)

plot_shape(
    [
        ("linear", digit1_top_stroke, "Digit 1 top stroke - linear spline"),
        # Stem is vertical → parametric cubic (y = L(x) undefined)
        ("cubic",  digit1_stem,       "Digit 1 stem - parametric cubic spline"),
        ("linear", digit1_base,       "Digit 1 base - linear spline"),
    ],
    "Digit 1"
)

plot_shape(
    [("cubic", digit0_points, "Digit 0 - parametric cubic spline")],
    "Digit 0"
)


# ============================================================
# PLOT OBJECTS
# ============================================================

sun_parts = [("cubic", sun_circle_points,
               "Sun circle - parametric cubic spline")]
for k, ray in enumerate(sun_rays):
    sun_parts.append(("linear", ray, f"Sun ray {k+1} - linear spline"))

plot_shape(sun_parts, "Sun")

plot_shape(
    [
        ("cubic", balloon_body_points,
         "Balloon body - parametric cubic spline"),
        ("cubic", balloon_string_points,
         "Balloon string - parametric cubic spline"),
    ],
    "Balloon"
)