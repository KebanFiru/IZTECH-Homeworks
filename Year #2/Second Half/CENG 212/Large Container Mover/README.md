# Large Container Mover (Scheme)

This project implements the function:

`(move-containers-to-platform n containers)`

for the assignment scenario where containers are moved from an initial stack to a target stack with a middle platform.

## Problem Rules Implemented

1. A heavier container cannot be placed on top of a lighter one.
2. Container weights are unique.
3. Number of containers must be 4, 5, or 6.
4. Middle platform has a weight limit:
	- `middle-limit = 3/4 * (total weight of all containers)`
5. Each move is printed with:
	- moved container weight
	- from/to location
	- current middle platform weight
6. The function prints `SUCCESS` or `FAILURE` at the end.

Additional input validation in code:

- Container weights must be between `10` and `100`.
- Weight values must be unique.
- The container list length must match `n`.

## Approach

The solution uses a Tower-of-Hanoi style recursive strategy with three stacks:

- `initial`
- `middle`
- `target`

Before each move, the code checks:

- stack order rule (no heavier on lighter)
- middle platform weight limit (for moves to `middle`)

If any move violates constraints, execution stops with `FAILURE`.


## Included Tests

1. Example from assignment: `(move-containers-to-platform 5 '(20 30 50 60 80))`
2. Success case: `(move-containers-to-platform 4 '(10 20 30 40))`
3. Failure case (middle limit exceeded): `(move-containers-to-platform 6 '(46 47 48 49 50 51))`

## Notes

- The function returns the symbol `'SUCCESS` or `'FAILURE` in addition to printing logs.
- If `n` does not match list length, `n` is not 4/5/6, any weight is outside 10-100, or any weight is duplicated, input is rejected with `FAILURE`.
