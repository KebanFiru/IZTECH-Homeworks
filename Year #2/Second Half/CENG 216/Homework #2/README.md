# Homework 2 — Interpolation with Splines

This project demonstrates parametric natural cubic splines for curved/closed shapes and ordinary linear splines for straight segments, as required by the assignment.

Files
- `main.py`: Complete, runnable example that constructs digits `3, 2, 0` and objects `cloud, fish`, computes splines, prints interval polynomials to the console, and saves plots to the `plots/` directory.
- `requirements.txt`: Python dependencies.

Run

Install dependencies (recommended in a virtualenv):

```bash
python3 -m pip install -r requirements.txt
```

Then run:

```bash
python3 main.py
```

Outputs
- Generated plots will be saved in `plots/Digits_3_2_0.png` and `plots/Objects_cloud_fish.png`.

Notes
- The script sets Matplotlib's backend to `Agg` so it works in headless environments and automatically saves PNGs.
- Control points in `main.py` are illustrative; adjust them if you wish to refine shapes or to include additional digits/objects.
