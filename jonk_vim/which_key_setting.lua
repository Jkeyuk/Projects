-- Which Key Setup

local wk = require("which-key")
wk.register({
	["<leader>"] = {
		e = { "diag open float" },
		q = { "diag list" },
		g = { "<cmd>Neogit<cr>", "Neogit" },
		f = {
			name = "+Tele Find",
			c = { "<cmd>Telescope command_history<cr>", "command history" },
			f = { "<cmd>Telescope find_files<cr>", "Find File" },
			g = { "<cmd>Telescope live_grep<cr>", "Live Grep" },
			b = { "<cmd>Telescope buffers<cr>", "Buffers" },
			h = { "<cmd>Telescope help_tags<cr>", "Help Tags" },
		},
		w = {
			name = "+lsp",
			f = { "format" },
			a = { "add worspace folder" },
			r = { "remove worspace folder" },
			l = { "list workspace folder" },
			s = { "<cmd>Telescope lsp_document_symbols<cr>", "doc symbols" },
		},
		D = { "lsp type definition" }
	},
})
