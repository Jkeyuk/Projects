local colors_p_cappuc = {
    black = "#45475A",
    blue = "#89B4FA",
    cyan = "#94E2D5",
    green = "#A6E3A1",
    magenta = "#F5C2E7",
    red = "#F38BA8",
    white = "#CDD6F4",
    yellow = "#F9E2AF",
    purple = "#ae94e2",
    orange = "#facf89"
}

local cappu = {
    identifier = colors_p_cappuc.blue,
    func = colors_p_cappuc.green,
    statement = colors_p_cappuc.orange,
    structure = colors_p_cappuc.orange,
    stringg = colors_p_cappuc.red,
    comment = colors_p_cappuc.red,
    type = colors_p_cappuc.red,
    warning = colors_p_cappuc.yellow,
    err = colors_p_cappuc.red,
}

local cappu_2 = {
    identifier = colors_p_cappuc.blue,
    func = colors_p_cappuc.purple,
    statement = colors_p_cappuc.green,
    structure = colors_p_cappuc.green,
    stringg = colors_p_cappuc.orange,
    comment = colors_p_cappuc.orange,
    type = colors_p_cappuc.orange,
    warning = colors_p_cappuc.yellow,
    err = colors_p_cappuc.red,
}

function SetSyntaxHl(cols)
    -- Comment
    vim.api.nvim_set_hl(0, "Comment", {
        fg = cols.comment
    })

    -- Diagno
    vim.api.nvim_set_hl(0, "DiagnosticWarn", {
        fg = cols.warning
    })

    -- Ident
    vim.api.nvim_set_hl(0, "Identifier", {
        fg = cols.identifier
    })

    -- Fucntion/Method
    vim.api.nvim_set_hl(0, "Function", {
        fg = cols.func
    })
    -- Structure
    vim.api.nvim_set_hl(0, "Structure", {
        fg = cols.structure
    })
    vim.api.nvim_set_hl(0, "Special", {
        fg = cols.structure
    })
    vim.api.nvim_set_hl(0, "Delimeter", {
        fg = cols.structure
    })
    vim.api.nvim_set_hl(0, "Delimiter", {
        fg = cols.structure
    })
    vim.api.nvim_set_hl(0, "Operator", {
        fg = cols.structure
    })

    -- Statement
    vim.api.nvim_set_hl(0, "Statement", {
        fg = cols.statement
    })
    vim.api.nvim_set_hl(0, "Macro", {
        fg = cols.statement
    })

    -- string/number
    vim.api.nvim_set_hl(0, "String", {
        fg = cols.stringg
    })
    vim.api.nvim_set_hl(0, "Boolean", {
        fg = cols.stringg
    })
    vim.api.nvim_set_hl(0, "Number", {
        fg = cols.stringg
    })
    vim.api.nvim_set_hl(0, "Character", {
        fg = cols.stringg
    })

    -- Type
    vim.api.nvim_set_hl(0, "Type", {
        fg = cols.type
    })
    -- match paren
    vim.api.nvim_set_hl(0, "MatchParen", {
        bg = cols.identifier
    })
end

function SetRustHl(cols)
    SetSyntaxHl(cols);
    vim.api.nvim_set_hl(0, "Normal", {
        fg = cols.structure
    })
    vim.api.nvim_set_hl(0, "@lsp.type.struct", {
        fg = cols.type
    })
end

vim.api.nvim_create_user_command(
    'JonkSetRust',
    function(opts)
        if opts.fargs[1] == "cap1" then
            SetRustHl(cappu);
        end
        if opts.fargs[1] == "cap2" then
            SetRustHl(cappu_2);
        end
    end, {
        nargs = 1,
        complete = function(ArgLead, CmdLine, CursorPos)
            -- return completion candidates as a list-like table
            return { "cap1", "cap2", }
        end,
    }
)


vim.api.nvim_create_user_command(
    'JonkSetSyntaxHl',
    function(opts)
        if opts.fargs[1] == "cap1" then
            SetSyntaxHl(cappu);
        end
        if opts.fargs[1] == "cap2" then
            SetSyntaxHl(cappu_2);
        end
    end, {
        nargs = 1,
        complete = function(ArgLead, CmdLine, CursorPos)
            -- return completion candidates as a list-like table
            return { "cap1", "cap2", }
        end,
    }
)

SetRustHl(cappu_2);
