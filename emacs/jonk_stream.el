;; Stream related functions
(defun jonk-streammacs ()
  "Increase UI Scale for streaming"
  (interactive)
  (set-face-attribute `default nil :height 140)
  (set-face-attribute `mode-line  nil :height 1.3)
  (set-face-attribute `minibuffer-prompt  nil :height 2.0))


(defun jonk-streammacs-off ()
  "Turn off jonk-streammacs"
  (interactive)
  (set-face-attribute `default nil :height 120)
  (set-face-attribute `mode-line  nil :height 1.1)
  (set-face-attribute `minibuffer-prompt  nil :height 1.0))

;; Write function to set MOTD mode for buffer
(defun jonk-motd-buffer-mode()
  "Turn on MOTD mode"
  (interactive)
  (buffer-face-set `(:height 300)))

