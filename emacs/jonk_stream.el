;; Stream related functions

(defun jonk-streammacs ()
  "Increase UI Scale for streaming"
  (interactive)
  (set-face-attribute `default nil :height 140)
  (set-face-attribute `mode-line  nil :height 1.3))


(defun jonk-streammacs-off ()
  "Turn off jonk-streammacs"
  (interactive)
  (set-face-attribute `default nil :height 120)
  (set-face-attribute `mode-line  nil :height 1.1))

