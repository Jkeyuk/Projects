vim.keymap.set("n", "<leader>pv", vim.cmd.Ex)

vim.keymap.set("n", "<C-d>", "<C-d>zz")
vim.keymap.set("n", "<C-u>", "<C-u>zz")
vim.keymap.set("n", "n", "nzzzv")
vim.keymap.set("n", "N", "Nzzzv")

-- Global mappings.
-- See `:help vim.diagnostic.*` for documentation on any of the below functions
vim.keymap.set('n', '[d', vim.diagnostic.goto_prev)
vim.keymap.set('n', ']d', vim.diagnostic.goto_next)
vim.keymap.set('n', '<space>q', vim.diagnostic.setloclist)

-- greatest remap ever
vim.keymap.set("x", "<leader>p", [["_dP]])

-- Which Key Setup

local wk = require("which-key")
wk.register({
    ["<leader>"] = {
        e = { "<cmd>Telescope diagnostics<cr>", "Tele diagnostics" },
        q = { "diag list" },
        g = { "<cmd>Neogit<cr>", "Neogit" },
        f = {
            name = "+Tele Find",
            b = { "<cmd>Telescope buffers<cr>", "Buffers" },
            c = { "<cmd>Telescope command_history<cr>", "command history" },
            f = { "<cmd>Telescope find_files<cr>", "Find File" },
            g = { "<cmd>Telescope live_grep<cr>", "Live Grep" },
            h = { "<cmd>Telescope help_tags<cr>", "Help Tags" },
            r = { "<cmd>Telescope lsp_references<cr>", "Tele reference" },
            s = { "<cmd>Telescope lsp_document_symbols<cr>", "doc symbols" },
        },
        s = {
            name = "+save menu",
            s = { "<cmd>write<cr>", "save file" },
        },
        t = { "<cmd>Telescope<cr>", "Telescope" },
        w = {
            name = "+lsp",
            f = { "format" },
            F = { "<cmd>FormatWrite<cr>", "Formatter Format" },
            a = { "add worspace folder" },
            l = { "list workspace folder" },
        },
        D = { "lsp type definition" }
    },
})
vim.keymap.set('n', '<space>E', vim.diagnostic.open_float)
