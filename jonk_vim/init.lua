print("jonk package loaded")

-- initial configs
vim.o.number = true
vim.o.clipboard = "unnamedplus"
vim.o.relativenumber = true

vim.g.mapleader = " "
vim.keymap.set("n", "<leader>pv", vim.cmd.Ex)

-- Lazy Setup

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

require("lazy").setup("jonk_vim/plugin_list", opts)

-- Telescope Setup

local local_built = require('telescope.builtin')
vim.keymap.set('n', '<leader>ff', local_built.find_files, {})
vim.keymap.set('n', '<leader>fg', local_built.live_grep, {})
vim.keymap.set('n', '<leader>fb', local_built.buffers, {})
vim.keymap.set('n', '<leader>fh', local_built.help_tags, {})

-- Which Key Setup

local wk = require("which-key")
wk.register({
  ["<leader>"] = {
    f = {
      name = "+file",
      f = { "<cmd>Telescope find_files<cr>", "Find File" },
      g = { "<cmd>Telescope live_grep<cr>", "Live Grep" },
      b = { "<cmd>Telescope buffers<cr>", "Buffers" },
      h = { "<cmd>Telescope help_tags<cr>", "Help Tags" },
    },
  },
})
