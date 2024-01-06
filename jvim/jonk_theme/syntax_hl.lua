local colors_p = {
	black = "#45475A",
	blue = "#89B4FA",
	cyan = "#94E2D5",
	green = "#A6E3A1",
	magenta = "#F5C2E7",
	red = "#F38BA8",
	white = "#CDD6F4",
	yellow = "#F9E2AF",
}

local cappu = {
	comment = colors_p.red, -- red
	identifier = colors_p.blue, -- blue
	func = colors_p.green, -- green
	statement = colors_p.yellow, -- yellow
	stringg = colors_p.white, -- cappu text
	structure = colors_p.green, -- red
	warning = colors_p.yellow,
	err = colors_p.red,
}

local cappu_2 = {
	comment = colors_p.red, -- red
	identifier = colors_p.white, -- blue
	func = colors_p.green, -- green
	statement = colors_p.yellow, -- yellow
	stringg = colors_p.blue, -- cappu text
	structure = colors_p.green, -- red
	warning = colors_p.yellow,
	err = colors_p.red,
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
