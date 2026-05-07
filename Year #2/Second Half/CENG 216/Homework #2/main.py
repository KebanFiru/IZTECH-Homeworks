import numpy as np
import matplotlib.pyplot as plt


# ============================================================
# Selected digits: 3, 1, 0  (from student ID 320201066)
# Selected objects: Sun, Balloon  (from Figure 1)
#
# Methods used:
#   1. Parametric natural cubic splines for curved/closed shapes
#   2. Ordinary linear splines y = L(x) for straight line segments
#
# Note: At least 4 points are used for every spline segment.
#       Vertical segments are handled with parametric cubic splines
#       because y = L(x) is undefined for vertical lines.
# ============================================================


def natural_cubic_spline_coefficients(t, values):
    """
    Computes natural cubic spline coefficients.

    Each interval [t_i, t_{i+1}]:

        S_i(t) = a_i + b_i(t-t_i) + c_i(t-t_i)^2 + d_i(t-t_i)^3

    Conditions:
        1. Interpolation:    S_i(t_i) = v_i,  S_i(t_{i+1}) = v_{i+1}
        2. C1 continuity:    S'_{i-1}(t_i) = S'_i(t_i)
        3. C2 continuity:    S''_{i-1}(t_i) = S''_i(t_i)
        4. Natural BCs:      S''(t_0) = 0,    S''(t_n) = 0
    """
    t      = np.array(t,      dtype=float)
    values = np.array(values, dtype=float)
    n = len(t) - 1
    if n < 1:
        raise ValueError("At least two points are required.")
    h = np.diff(t)
    if np.any(h <= 0):
        raise ValueError("t must be strictly increasing.")

    A   = np.zeros((n + 1, n + 1))
    rhs = np.zeros(n + 1)
    A[0, 0] = 1
    A[n, n] = 1
    for i in range(1, n):
        A[i, i - 1] = h[i - 1]
        A[i, i]     = 2 * (h[i - 1] + h[i])
        A[i, i + 1] = h[i]
        rhs[i] = 3 * (
            (values[i + 1] - values[i]) / h[i]
            - (values[i] - values[i - 1]) / h[i - 1]
        )
    c_full = np.linalg.solve(A, rhs)
    a = values[:-1].copy()
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
    """Evaluates S_i(t) over every interval."""
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
    Parametric natural cubic spline: t_i = i.
    Fits X(t) and Y(t) separately. Prints derived equations.
    """
    points = np.array(points, dtype=float)
    t      = np.arange(len(points), dtype=float)
    ax, bx, cx, dx = natural_cubic_spline_coefficients(t, points[:, 0])
    ay, by, cy, dy = natural_cubic_spline_coefficients(t, points[:, 1])
    xs = evaluate_cubic_spline(t, ax, bx, cx, dx)
    ys = evaluate_cubic_spline(t, ay, by, cy, dy)

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
    Ordinary linear splines: L_i(x) = y_i + m_i(x - x_i)
    Prints derived equations. Raises error on vertical segments.
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
            raise ValueError(
                f"Vertical line detected between {points[i]} and {points[i+1]}. "
                "Use parametric cubic spline instead."
            )
        m  = (y1 - y0) / (x1 - x0)
        xs = np.linspace(x0, x1, samples_per_interval)
        ys = y0 + m * (xs - x0)
        xs_all.extend(xs)
        ys_all.extend(ys)
        print(f"Interval {i}: {min(x0,x1):.4f} <= x <= {max(x0,x1):.4f}")
        print(f"L_{i}(x) = {y0:.4f} + ({m:.4f})(x - {x0:.4f})")
        print()
    return np.array(xs_all), np.array(ys_all)


def plot_shape(parts, title, xlim=(0, 10), ylim=(0, 11), extra_scatter=None):
    """
    Plots one digit or object.
    parts = [(method, points, label), ...]   method = 'cubic' | 'linear'
    """
    plt.figure(figsize=(6, 6))
    plt.title(title)

    COLORS = ['#E63946', '#457B9D', '#2A9D8F', '#E9C46A',
              '#F4A261', '#6A4C93', '#1982C4', '#8AC926',
              '#FF595E', '#6A4C93']

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
            plt.scatter([ex], [ey], s=60, color='black', zorder=6, label=elabel)

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
# ============================================================

# ----------------------------------------------------------
# Digit 3  —  parametric natural cubic spline
# ----------------------------------------------------------
digit3_points = [
    (1.5, 8.5),
    (3.5, 9.8),
    (5.5, 8.2),
    (3.8, 6.8),
    (5.5, 5.2),
    (3.5, 3.5),
    (1.5, 4.5),
]

# ----------------------------------------------------------
# Digit 1
#   Top stroke : ordinary linear spline   (4 points, diagonal)
#   Stem       : parametric cubic spline  (4 points, vertical → no y=L(x))
#   Base bar   : ordinary linear spline   (4 points, horizontal)
# ----------------------------------------------------------
digit1_top_stroke = [
    (2.5, 9.0),
    (3.2, 9.4),
    (3.8, 9.7),
    (4.5, 10.0),
]

# Vertical → must use parametric cubic (y = L(x) undefined for vertical lines)
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
# Digit 0  —  closed parametric natural cubic spline
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
    (4.0, 9.5),   # closed
]

# ----------------------------------------------------------
# Sun
#   Circle : closed parametric natural cubic spline (9 points)
#   Rays   : ordinary linear splines (4 points each)
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
    (5.0, 7.8),   # closed
]

sun_rays = [
    [(5.0, 8.0), (5.1, 8.5), (4.9, 9.0), (5.0, 9.6)],          # N (slight offset for linear)
    [(6.0, 7.3), (6.5, 7.8), (7.0, 8.3), (7.5, 8.8)],           # NE
    [(7.0, 5.5), (7.5, 5.5), (8.0, 5.5), (8.6, 5.5)],           # E
    [(6.0, 3.7), (6.5, 3.2), (7.0, 2.7), (7.5, 2.2)],           # SE
    [(5.0, 3.0), (5.1, 2.5), (4.9, 2.0), (5.0, 1.5)],           # S (slight offset for linear)
    [(4.0, 3.7), (3.5, 3.2), (3.0, 2.7), (2.5, 2.2)],           # SW
    [(3.0, 5.5), (2.5, 5.5), (2.0, 5.5), (1.4, 5.5)],           # W
    [(4.0, 7.3), (3.5, 7.8), (3.0, 8.3), (2.5, 8.8)],           # NW
]

# ----------------------------------------------------------
# Balloon
#   Body   : closed parametric natural cubic spline (9 points)
#   String : parametric natural cubic spline        (5 points)
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
    (5.0, 9.5),   # closed
]

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

sun_parts = [("cubic", sun_circle_points, "Sun circle - parametric cubic spline")]
for k, ray in enumerate(sun_rays):
    sun_parts.append(("linear", ray, f"Sun ray {k+1} - linear spline"))

plot_shape(sun_parts, "Sun")

plot_shape(
    [
        ("cubic", balloon_body_points,   "Balloon body - parametric cubic spline"),
        ("cubic", balloon_string_points, "Balloon string - parametric cubic spline"),
    ],
    "Balloon"
)