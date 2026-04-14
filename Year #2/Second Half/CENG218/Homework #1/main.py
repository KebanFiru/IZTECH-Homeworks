def find_kth_two(A, B, k, iA=0, iB=0):
    """Helper function to find the k-th element between two sorted arrays."""
    lenA, lenB = len(A) - iA, len(B) - iB
 
    # Base cases: if one array is exhausted, the answer is in the other
    if lenA == 0: return B[iB + k - 1]
    if lenB == 0: return A[iA + k - 1]
 
    # If k is 1, return the smallest of the current first elements
    if k == 1: return min(A[iA], B[iB])
 
    # Divide k by 2 for the two-array logic
    step = max(1, k // 2)
 
    valA = A[iA + step - 1] if step <= lenA else float('inf')
    valB = B[iB + step - 1] if step <= lenB else float('inf')
 
    # Discard the smaller chunk and recurse
    if valA <= valB:
        return find_kth_two(A, B, k - step, iA + step, iB)
    else:
        return find_kth_two(A, B, k - step, iA, iB + step)
 
def find_kth_three(A, B, C, k, iA=0, iB=0, iC=0):
    """Main divide and conquer function for three sorted arrays."""
    lenA, lenB, lenC = len(A) - iA, len(B) - iB, len(C) - iC
 
    # If any array is exhausted, fall back to the 2-array logic
    if lenA == 0: return find_kth_two(B, C, k, iB, iC)
    if lenB == 0: return find_kth_two(A, C, k, iA, iC)
    if lenC == 0: return find_kth_two(A, B, k, iA, iB)
 
    # Base case: if k is 1, return the smallest available element
    if k == 1: return min(A[iA], B[iB], C[iC])
 
    # Divide k by 3 to find the safe step size for elimination
    step = max(1, k // 3)
 
    # Fetch values, use infinity if the step exceeds the remaining array length
    valA = A[iA + step - 1] if step <= lenA else float('inf')
    valB = B[iB + step - 1] if step <= lenB else float('inf')
    valC = C[iC + step - 1] if step <= lenC else float('inf')
 
    # Find which array has the smallest step-value
    min_val = min(valA, valB, valC)
 
    # Discard the elements from the array that yielded the minimum value
    if min_val == valA:
        return find_kth_three(A, B, C, k - step, iA + step, iB, iC)
    elif min_val == valB:
        return find_kth_three(A, B, C, k - step, iA, iB + step, iC)
    else:
        return find_kth_three(A, B, C, k - step, iA, iB, iC + step)
 
def find_median_three_arrays(A, B, C):
    n = len(A)
    # Total elements = 3n. For an odd number, median is at (3n // 2) + 1
    k = (3 * n) // 2 + 1
    return find_kth_three(A, B, C, k)
 
# Given test data from the image
A = [10, 20, 80, 110, 120]
B = [5, 15, 90, 130, 140]
C = [30, 40, 50, 60, 150]
 
print(f"The median is: {find_median_three_arrays(A, B, C)}")
# Output: The median is: 60