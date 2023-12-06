
(require 'package)
;; Comment/uncomment this line to enable MELPA Stable if desired.  See `package-archive-priorities`
;; and `package-pinned-packages`. Most users will not need or want to do this.
(add-to-list 'package-archives '("melpa-stable" . "https://stable.melpa.org/packages/") t)
(package-initialize)

(use-package which-key :ensure t)
(which-key-mode)

(use-package company :ensure t)
(global-company-mode)
(use-package yasnippet :ensure t)
(use-package yasnippet-snippets :ensure t)
(use-package magit :ensure t)

(menu-bar-mode -1)
(scroll-bar-mode -1)
(tool-bar-mode -1)

(setq make-backup-files nil);

