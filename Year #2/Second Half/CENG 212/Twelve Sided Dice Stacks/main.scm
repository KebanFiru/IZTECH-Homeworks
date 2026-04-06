; Opposite face on a normal (non-top) die (faces sum to 13)
(define (opposite-face-summing-to-13 face)
  (- 13 face))

; Is face a legal 12-sided die face?
(define (valid-die-face? face)
  (and (integer? face) (>= face 1) (<= face 12)))


(define (find-top-face-candidates dice-count touching-face-sum bottom-visible-face)

  ; The opposite of the known visible face (also hidden, on the other side)
  (define hidden-opposite-face (opposite-face-summing-to-13 bottom-visible-face))

  ; A ground-face candidate b is valid when:
  ;   - It is a face 1-12.
  ;   - Its opposite (= top face of bottom die) is also 1-12.
  ;   - Neither b nor opposite-normal(b) coincides with the visible face
  ;     or its opposite (those four positions are already "taken").
  (define (valid-ground-face? bottom-face)
    (let ((top-face-of-bottom-die (opposite-face-summing-to-13 bottom-face)))
      (and (valid-die-face? bottom-face)
           (valid-die-face? top-face-of-bottom-die)
           (not (= bottom-face bottom-visible-face))
           (not (= bottom-face hidden-opposite-face))
           (not (= top-face-of-bottom-die bottom-visible-face))
           (not (= top-face-of-bottom-die hidden-opposite-face)))))

  ; Propagate upward through the stack.
  ;   steps-left : how many more dice transitions remain
  ;   current-top-face : top face of the current die
  ;   final-step?      : is the NEXT transition the one into the topmost die?
  (define (propagate-stack steps-left current-top-face final-step?)
    (cond
      ; No more dice above: cur-top is the final answer
      ((= steps-left 0)
       (if (valid-die-face? current-top-face)
           (list current-top-face)
           '()))
      ; One or more dice remain above
      (else
       (let* ((next-bottom-face (- touching-face-sum current-top-face))
        (next-top-face (opposite-face-summing-to-13 next-bottom-face)))
         (if (and (valid-die-face? next-bottom-face) (valid-die-face? next-top-face))
             ; Continue propagation; the step after this is "last" when
             ; (steps-left - 1) == 1, i.e. steps-left == 2
             (propagate-stack (- steps-left 1)
                              next-top-face
                              (= steps-left 2))
             ; Invalid intermediate face – prune this branch
             '())))))

  ; Collect results over all valid ground-faces
  (define (collect-candidates ground-face-list accumulated-candidates)
    (if (null? ground-face-list)
        accumulated-candidates
        (let* ((bottom-face (car ground-face-list))
               (top-face-of-bottom-die (opposite-face-summing-to-13 bottom-face))
               (transitions-left (- dice-count 1))
           ; Start propagation from the bottom die toward the top.
           (candidate-results (propagate-stack transitions-left top-face-of-bottom-die (= transitions-left 1))))
          (collect-candidates (cdr ground-face-list) (append candidate-results accumulated-candidates)))))

  ; Build candidate list
  (define possible-ground-faces
    (filter valid-ground-face? '(1 2 3 4 5 6 7 8 9 10 11 12)))

  (define raw-candidates (collect-candidates possible-ground-faces '()))

  ; Remove duplicates (simple helper – no built-in remove-duplicates in Pretty Big)
  (define (deduplicate-values candidate-list seen-values)
    (cond ((null? candidate-list) '())
          ((member (car candidate-list) seen-values)
           (deduplicate-values (cdr candidate-list) seen-values))
          (else
           (cons (car candidate-list)
                 (deduplicate-values (cdr candidate-list) (cons (car candidate-list) seen-values))))))

  (define unique-candidates (filter valid-die-face? (deduplicate-values raw-candidates '())))

  ; Sort ascending (sort is available in Pretty Big)
  (sort unique-candidates <))


(newline)
(display "Test 1 - (find-top-face-candidates 4 11 10)") (newline)
(display "  Result   : ") (display (find-top-face-candidates 4 11 10)) (newline)
(newline)

(display "Test 2 - (find-top-face-candidates 3 9 5)") (newline)
(display "  Result   : ") (display (find-top-face-candidates 3 9 5)) (newline)
(newline)

(display "Test 3 - (find-top-face-candidates 3 11 12)") (newline)
(display "  Result   : ") (display (find-top-face-candidates 3 11 12)) (newline)
(newline)

(display "Test 4 - (find-top-face-candidates 5 11 6)") (newline)
(display "  Result   : ") (display (find-top-face-candidates 5 11 6)) (newline)
(newline)

(display "Test 5 - (find-top-face-candidates 4 10 3)") (newline)
(display "  Result   : ") (display (find-top-face-candidates 4 10 3)) (newline)
(newline)

(display "Test 6 - (find-top-face-candidates 3 8 1)") (newline)
(display "  Result   : ") (display (find-top-face-candidates 3 8 1)) (newline)
(newline)