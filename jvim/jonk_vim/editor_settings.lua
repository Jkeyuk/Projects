-- initial configs
vim.o.number = true
vim.o.clipboard = "unnamedplus"
vim.o.relativenumber = true
vim.o.termguicolors = true
vim.o.tabstop = 4

vim.o.cursorline = true
vim.o.cursorlineopt = 'number'

vim.g.mapleader = " "
vim.keymap.set("n", "<leader>pv", vim.cmd.Ex)

-- Global mappings.
-- See `:help vim.diagnostic.*` for documentation on any of the below functions
vim.keymap.set('n', '[d', vim.diagnostic.goto_prev)
vim.keymap.set('n', ']d', vim.diagnostic.goto_next)
vim.keymap.set('n', '<space>q', vim.diagnostic.setloclist)

