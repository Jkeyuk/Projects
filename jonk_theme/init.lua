require("jonk_theme.lua_Line_theme")

local electric_blue = {
	primary = '#4ee8fc',
	primary_text = '#000000',
	secondary = '#4ffc9d',
	yellow = '#e8fc4f',
	red = '#fc634f',
	bg = '#000000'
}

function ApplyTheme(cols)
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
		fg = cols.red
	})
end

vim.api.nvim_create_user_command(
	'JonkThemeElectricBlue',
	function()
		ApplyTheme(electric_blue)
	end,
	{})
