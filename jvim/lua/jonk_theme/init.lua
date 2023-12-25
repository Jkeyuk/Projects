require("jonk_theme.lua_Line_theme")

local cap_green = {
	primary = '#CDD6F4',
	secondary = '#A6E3A1',
	warn = '#e8fc4f',
	err = '#fc634f',
	bg = '#1E1E2E'
}


local electric_blue = {
	primary = '#4ee8fc',
	primary_text = '#000000',
	secondary = '#4ffc9d',
	warn = '#e8fc4f',
	err = '#fc634f',
	bg = '#000000'
}

function ApplyTheme(cols)
	vim.cmd.colorscheme('murphy')
	vim.api.nvim_set_hl(0, "Normal", {
		fg = cols.primary,
		bg = cols.bg
	})
	vim.api.nvim_set_hl(0, "EndOfBuffer", {
		bg = cols.bg
	})
	vim.api.nvim_set_hl(0, "LineNr", {
		fg = cols.primary
	})
	vim.api.nvim_set_hl(0, "WinSeparator", {
		fg = cols.secondary
	})
	vim.api.nvim_set_hl(0, "CursorLineNr", {
		fg = cols.err
	})
end

vim.api.nvim_create_user_command(
	'JonkThemeElectricBlue',
	function()
		ApplyTheme(cap_green)
	end,
	{})
ApplyTheme(cap_green)
require("jonk_theme.syntax_hl")
