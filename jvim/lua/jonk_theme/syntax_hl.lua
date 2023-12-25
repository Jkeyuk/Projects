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
end

vim.api.nvim_create_user_command(
	'JonkSetSyntaxHllll',
	function()
		SetSyntaxHl(cappu);
	end,
	{})
SetSyntaxHl(cappu);
