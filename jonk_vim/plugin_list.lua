return {
	{
		'nvim-lualine/lualine.nvim',
		dependencies = {
			'nvim-tree/nvim-web-devicons'
		},
		config = function()
			require("lualine").setup({
				options = {
					icons_enabled = true,
					theme = "powerline_dark",
				}
			})
		end,
	},
	{
		'nvim-telescope/telescope.nvim', tag = '0.1.5',
		-- or , branch = '0.1.x',
      dependencies = { 'nvim-lua/plenary.nvim' }
    }
    ,{"nvim-treesitter/nvim-treesitter", build = ":TSUpdate"}
}
