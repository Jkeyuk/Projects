print("jonk package loaded")
require("jonk_vim.editor_settings")
require("jonk_vim.lazy_settings")
require("jonk_vim.lsp_settings")
require("jonk_vim.telescope_settings")
require("jonk_vim.cmp_settings")
require("jonk_vim.formatter")
require("jonk_vim.theme_commands")
require("jonk_vim.which_key_setting")

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
