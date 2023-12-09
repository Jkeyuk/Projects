print("hello")
vim.o.number = true
vim.opt.clipboard = 'unnamedplus'

local lazypath = vim.fn.stdpath("data") .. "/lazy/lazy.nvim"
if not vim.loop.fs_stat(lazypath) then
  vim.fn.system({
    "git",
    "clone",
    "--filter=blob:none",
    "https://github.com/folke/lazy.nvim.git",
    "--branch=stable", -- latest stable release
    lazypath,
  })
end
vim.opt.rtp:prepend(lazypath)

require("lazy").setup({
	{
		'nvim-lualine/lualine.nvim',
		config = function()
			require('lualine').setup {
				options = {	
					icons_enabled = true,
					theme = 'horizon',
				}
			}
		end,
	},
	{
    'nvim-telescope/telescope.nvim', tag = '0.1.5',
-- or                              , branch = '0.1.x',
      dependencies = { 'nvim-lua/plenary.nvim' }
    }
    ,{"nvim-treesitter/nvim-treesitter", build = ":TSUpdate"}
}, opts)

