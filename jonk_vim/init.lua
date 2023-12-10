print("jonk package loaded")
require("jonk_vim.editor_settings")
require("jonk_vim.lazy_settings")
require("jonk_vim.lsp_settings")
require("jonk_vim.telescope_settings")
require("jonk_vim.cmp_settings")
require("jonk_vim.formatter")
-- Which Key Setup

local wk = require("which-key")
wk.register({
	["<leader>"] = {
		e = { "diag open float" },
		q = { "diag list" },
		f = {
			name = "+file",
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
		},
		D = { "lsp type definition" }
	},
})

require("nvim-treesitter.configs").setup {
	ensure_installed = {
		"lua",
		"javascript",
		"typescript",
		"c",
		"c_sharp",
		"java",
		"json",
		"python",
		"html"
	},
	highlight = {
		enable = false,
	},
	incremental_selection = {
		enable = true,
		keymaps = {
			init_selection = "gnn", -- set to `false` to disable one of the mappings
			node_incremental = "grn",
			scope_incremental = "grc",
			node_decremental = "grm",
		},
	}
}
