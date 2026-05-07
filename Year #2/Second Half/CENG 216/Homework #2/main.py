import numpy as np
import matplotlib.pyplot as plt


# ============================================================
# Selected digits: 3, 2, 0  (from student ID 320201066)
# Selected objects: Cloud, Fish  (from Figure 1)
#
# Methods used:
#   1. Parametric natural cubic splines for curved/closed shapes
#   2. Ordinary linear splines y = L(x) for straight line segments
#
# Why parametric cubic splines?
#   Some shapes, such as 0, cloud, and fish body/tail, cannot be
#   represented conveniently as a single function y = S(x), because
#   the same x-value may correspond to multiple y-values.
#
#   Therefore, for curved shapes, we write:
#
#       x = X(t)
#       y = Y(t)
#
#   where t is an artificial parameter that moves along the curve.
#   For selected points P_i = (x_i, y_i) we assign t_i = i,
#   then compute one natural cubic spline for X(t) and another for Y(t).
#
# Why ordinary linear splines?
#   Straight segments such as the bottom of digit 2 are handled with:
#
#       L_i(x) = y_i + m_i(x - x_i)
#
#   where m_i = (y_{i+1} - y_i) / (x_{i+1} - x_i)
#
# Note: At least 4 points are used for every spline segment.
# ============================================================


# ============================================================
# SPLINE FUNCTIONS
# ============================================================

def natural_cubic_spline_coefficients(t, values):
    """
    Computes the natural cubic spline coefficients for one coordinate.

    Used twice per parametric curve: once for X(t), once for Y(t).

    Each interval [t_i, t_{i+1}] has a cubic polynomial:

        S_i(t) = a_i
               + b_i(t - t_i)
               + c_i(t - t_i)^2
               + d_i(t - t_i)^3

    Natural cubic spline conditions:
        1. Interpolation:       S_i(t_i) = v_i,  S_i(t_{i+1}) = v_{i+1}
        2. C1 continuity:       S'_{i-1}(t_i)  = S'_i(t_i)
        3. C2 continuity:       S''_{i-1}(t_i) = S''_i(t_i)
        4. Natural endpoints:   S''(t_0) = 0,    S''(t_n) = 0
    """
    t      = np.array(t,      dtype=float)
    values = np.array(values, dtype=float)
    n = len(t) - 1

    if n < 1:
        raise ValueError("At least two points are required.")

    h = np.diff(t)
    if np.any(h <= 0):
        raise ValueError("Parameter values t must be strictly increasing.")

    # Build tridiagonal system  A·c = rhs
    A   = np.zeros((n + 1, n + 1))
    rhs = np.zeros(n + 1)

    A[0, 0] = 1    # c_0 = 0  (S''(t_0) = 0)
    A[n, n] = 1    # c_n = 0  (S''(t_n) = 0)

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
    """
    Evaluates S_i(t) over every interval.

    For each interval [t_i, t_{i+1}]:

        S_i(t) = a_i
               + b_i(t - t_i)
               + c_i(t - t_i)^2
               + d_i(t - t_i)^3
    """
    evaluated_values = []

    for i in range(len(a)):
        ts   = np.linspace(t_nodes[i], t_nodes[i + 1], samples_per_interval)
        vals = (
            a[i]
            + b[i] * (ts - t_nodes[i])
            + c[i] * (ts - t_nodes[i]) ** 2
            + d[i] * (ts - t_nodes[i]) ** 3
        )
        evaluated_values.extend(vals)

    return np.array(evaluated_values)


def parametric_cubic_spline(points, name):
    """
    Builds a parametric natural cubic spline: t_i = i, then fits
    X(t) and Y(t) separately. Prints all derived equations.
    """
    points   = np.array(points, dtype=float)
    t        = np.arange(len(points), dtype=float)
    x_values = points[:, 0]
    y_values = points[:, 1]

    ax, bx, cx, dx = natural_cubic_spline_coefficients(t, x_values)
    ay, by, cy, dy = natural_cubic_spline_coefficients(t, y_values)

    xs = evaluate_cubic_spline(t, ax, bx, cx, dx)
    ys = evaluate_cubic_spline(t, ay, by, cy, dy)

    print("\n" + "=" * 80)
    print(name)
    print("=" * 80)

    for i in range(len(ax)):
        print(f"Interval {i}: {t[i]:.1f} <= t <= {t[i+1]:.1f}")
        print(
            f"X_{i}(t) = {ax[i]:.4f} "
            f"+ ({bx[i]:.4f})(t - {t[i]:.4f}) "
            f"+ ({cx[i]:.4f})(t - {t[i]:.4f})^2 "
            f"+ ({dx[i]:.4f})(t - {t[i]:.4f})^3"
        )
        print(
            f"Y_{i}(t) = {ay[i]:.4f} "
            f"+ ({by[i]:.4f})(t - {t[i]:.4f}) "
            f"+ ({cy[i]:.4f})(t - {t[i]:.4f})^2 "
            f"+ ({dy[i]:.4f})(t - {t[i]:.4f})^3"
        )
        print()

    return xs, ys


def linear_spline(points, name, samples_per_interval=100):
    """
    Builds ordinary linear splines:  L_i(x) = y_i + m_i(x - x_i)

    Prints all derived equations.
    Raises ValueError for vertical segments (undefined slope).
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
                "Ordinary y = L(x) linear splines cannot handle vertical lines."
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
    Plots one digit or object and calls plt.show().

    parts         : [(method, points, label), ...]
                    method = "cubic" | "linear"
    extra_scatter : [(x, y, label), ...]  single scatter points (e.g. eye)
    """
    plt.figure(figsize=(6, 6))
    plt.title(title)

    for method, points, label in parts:
        if method == "cubic":
            xs, ys = parametric_cubic_spline(points, label)
        elif method == "linear":
            xs, ys = linear_spline(points, label)
        else:
            raise ValueError("Method must be 'cubic' or 'linear'.")

        plt.plot(xs, ys, label=label)
        p = np.array(points, dtype=float)
        plt.scatter(p[:, 0], p[:, 1], s=20)

    if extra_scatter:
        for ex, ey, elabel in extra_scatter:
            plt.scatter([ex], [ey], s=40, label=elabel)

    plt.xlabel("x")
    plt.ylabel("y")
    plt.grid(True)
    plt.axis("equal")
    plt.xlim(*xlim)
    plt.ylim(*ylim)
    plt.legend()
    plt.show()


# ============================================================
# CONTROL POINTS
# (Every spline segment uses at least 4 points — assignment requirement)
# ============================================================

# ----------------------------------------------------------
# Digit 3  —  parametric natural cubic spline
# ----------------------------------------------------------
digit3_points = [
    (2.0, 9.0),
    (4.0, 10.0),
    (6.0, 8.5),
    (4.2, 7.0),
    (6.0, 5.5),
    (4.0, 3.0),
    (2.0, 4.0),
]

# ----------------------------------------------------------
# Digit 2
#   Top curve  : parametric natural cubic spline  (4 points)
#   Diagonal   : ordinary linear spline           (4 points)
#   Bottom bar : ordinary linear spline           (4 points)
# ----------------------------------------------------------
digit2_top_curve = [
    (2.0, 8.0),
    (3.5, 10.0),
    (6.0, 9.0),
    (5.0, 7.0),
]

# 4-point diagonal — avoids any vertical segment
digit2_diagonal = [
    (5.0, 7.0),
    (4.0, 5.7),
    (3.0, 4.3),
    (2.0, 3.0),
]

# 4-point bottom bar (horizontal, left → right)
digit2_bottom = [
    (2.0, 3.0),
    (3.3, 3.0),
    (4.7, 3.0),
    (6.0, 3.0),
]

# ----------------------------------------------------------
# Digit 0  —  closed parametric natural cubic spline
# ----------------------------------------------------------
digit0_points = [
    (4.0, 9.0),
    (6.0, 8.0),
    (7.0, 5.5),
    (6.0, 3.0),
    (4.0, 2.0),
    (2.0, 3.0),
    (1.0, 5.5),
    (2.0, 8.0),
    (4.0, 9.0),   # same as first → closed curve
]

# ----------------------------------------------------------
# Cloud  —  closed parametric natural cubic spline
# ----------------------------------------------------------
cloud_points = [
    (1.0, 4.0),
    (1.5, 5.0),
    (2.3, 5.2),
    (2.8, 4.8),
    (3.4, 6.2),
    (4.3, 5.8),
    (4.7, 5.0),
    (5.5, 5.2),
    (6.2, 4.2),
    (5.7, 3.5),
    (4.5, 3.4),
    (3.3, 3.6),
    (2.2, 3.4),
    (1.0, 4.0),   # same as first → closed curve
]

# ----------------------------------------------------------
# Fish
#   Body  : closed parametric natural cubic spline  (7 points)
#   Tail  : parametric natural cubic spline         (5 points)
#   Fin   : ordinary linear spline                  (4 points)
#   Eye   : single scatter point
# ----------------------------------------------------------
fish_body_points = [
    (2.0, 5.0),
    (3.5, 6.5),
    (6.0, 6.2),
    (7.5, 5.0),
    (6.0, 3.8),
    (3.5, 3.5),
    (2.0, 5.0),   # closed
]

# Tail drawn as a closed parametric cubic (no vertical segments)
fish_tail_points = [
    (2.0, 5.0),
    (1.0, 6.0),
    (1.3, 5.0),
    (1.0, 4.0),
    (2.0, 5.0),   # closed
]

# Fin uses 4 points — no vertical segment
fish_fin_points = [
    (4.5, 6.0),
    (4.2, 6.8),
    (4.8, 7.2),
    (5.3, 6.2),
]


# ============================================================
# PLOT DIGITS
# ============================================================

plot_shape(
    [
        ("cubic", digit3_points, "Digit 3 - parametric cubic spline"),
    ],
    "Digit 3"
)

plot_shape(
    [
        ("cubic",  digit2_top_curve, "Digit 2 top - cubic spline"),
        ("linear", digit2_diagonal,  "Digit 2 diagonal - linear spline"),
        ("linear", digit2_bottom,    "Digit 2 bottom - linear spline"),
    ],
    "Digit 2"
)

plot_shape(
    [
        ("cubic", digit0_points, "Digit 0 - parametric cubic spline"),
    ],
    "Digit 0"
)


# ============================================================
# PLOT OBJECTS
# ============================================================

plot_shape(
    [
        ("cubic", cloud_points, "Cloud - parametric cubic spline"),
    ],
    "Cloud"
)

plot_shape(
    [
        ("cubic",  fish_body_points, "Fish body - parametric cubic spline"),
        ("cubic",  fish_tail_points, "Fish tail - parametric cubic spline"),
        ("linear", fish_fin_points,  "Fish fin  - linear spline"),
    ],
    "Fish",
    extra_scatter=[(6.8, 5.3, "Fish eye")]
)