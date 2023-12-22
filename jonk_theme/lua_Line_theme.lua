local electric_blue = {
	black        = '#000000',
	white        = '#f9f9f9',
	red          = '#fc634f',
	green        = '#b8bb26',
	secondary    = '#4ffc9d',
	yellow       = '#e8fc4f',
	primary      = '#4ee8fc',
	darkgray     = '#3c3836',
	lightgray    = '#504945',
	inactivegray = '#7c6f64',
}

function SetLuaLineCol(colors)
	return {
		normal = {
			a = { bg = colors.primary, fg = colors.black, gui = 'bold' },
			b = { bg = colors.lightgray, fg = colors.white },
			c = { bg = colors.darkgray, fg = colors.primary }
		},
		insert = {
			a = { bg = colors.red, fg = colors.black, gui = 'bold' },
			b = { bg = colors.lightgray, fg = colors.white },
			c = { bg = colors.lightgray, fg = colors.white }
		},
		visual = {
			a = { bg = colors.yellow, fg = colors.black, gui = 'bold' },
			b = { bg = colors.lightgray, fg = colors.white },
			c = { bg = colors.inactivegray, fg = colors.black }
		},
		replace = {
			a = { bg = colors.red, fg = colors.black, gui = 'bold' },
			b = { bg = colors.lightgray, fg = colors.white },
			c = { bg = colors.black, fg = colors.white }
		},
		command = {
			a = { bg = colors.green, fg = colors.black, gui = 'bold' },
			b = { bg = colors.lightgray, fg = colors.white },
			c = { bg = colors.inactivegray, fg = colors.black }
		},
		inactive = {
			a = { bg = colors.darkgray, fg = colors.primary, gui = 'bold' },
			b = { bg = colors.darkgray, fg = colors.primary },
			c = { bg = colors.darkgray, fg = colors.primary }
		}
	}
end

require('lualine').setup { options = { theme = SetLuaLineCol(electric_blue) } }
