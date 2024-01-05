local cappu = {
	comment = '#F38BA8', -- red
	identifier = '#89B4FA', -- blue
	func = '#A6E3A1', -- green
	statement = '#F9E2AF', -- yellow
	stringg = '#CDD6F4', -- cappu text
	structure = '#F38BA8', -- red
	warning = '#e8fc4f',
	err = '#fc634f',
}

local cappu_2 = {
	comment = '#F38BA8',
	identifier = '#CDD6F4',
	func = '#A6E3A1',
	statement = '#F9E2AF',
	stringg = '#89B4FA',
	structure = '#F38BA8',
	warning = '#e8fc4f',
	err = '#fc634f',
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
	vim.api.nvim_set_hl(0, "Operator", {
		fg = cols.structure
	})

	-- Statement
	vim.api.nvim_set_hl(0, "Statement", {
		fg = cols.statement
	})

	-- string/number
	vim.api.nvim_set_hl(0, "String", {
		fg = cols.stringg
	})
	vim.api.nvim_set_hl(0, "Number", {
		fg = cols.stringg
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
end

vim.api.nvim_create_user_command(
	'JonkSetRust',
	function(opts)
		if opts.fargs[1] == "cap1" then
			print(opts.fargs)
			SetRustHl(cappu);
		end
		if opts.fargs[1] == "cap2" then
			print("cap2")
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
	'JonkSetSyntaxHllll',
	function()
		SetSyntaxHl(cappu_2);
	end,
	{})
SetSyntaxHl(cappu_2);
