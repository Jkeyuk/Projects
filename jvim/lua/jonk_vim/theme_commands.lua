function SetSyntaxHl()
	-- AccessModifier
	vim.api.nvim_set_hl(0, "csModifier", {
		fg = '#8e9bff'
	})

	vim.api.nvim_set_hl(0, "csAccessModifier", {
		fg = '#8e9bff'
	})
	vim.api.nvim_set_hl(0, "Storageclass", {
		fg = '#8e9bff'
	})

	-- Comment
	vim.api.nvim_set_hl(0, "Comment", {
		fg = '#fc634f'
	})
	-- Fucntion/Method
	vim.api.nvim_set_hl(0, "@lsp.type.function", {
		fg = '#4ffc9d'
	})
	vim.api.nvim_set_hl(0, "@function", {
		fg = '#4ffc9d'
	})
	vim.api.nvim_set_hl(0, "@lsp.type.method", {
		fg = '#4ffc9d'
	})
	vim.api.nvim_set_hl(0, "Function", {
		fg = '#4ffc9d'
	})

	-- class
	--		vim.api.nvim_set_hl(0, "@lsp.type.class", {
	--			fg = 'Orange'
	--		})
	--		vim.api.nvim_set_hl(0, "@lsp.type.struct", {
	--			fg = 'Orange'
	--		})
	vim.api.nvim_set_hl(0, "Structure", {
		fg = '#4ee8fc'
	})
	vim.api.nvim_set_hl(0, "Special", {
		fg = '#4ee8fc'
	})
	vim.api.nvim_set_hl(0, "Operator", {
		fg = '#4ee8fc'
	})

	-- Enum
	vim.api.nvim_set_hl(0, "@lsp.type.enum", {
		fg = 'Orange'
	})
	vim.api.nvim_set_hl(0, "@lsp.type.enumMember", {
		fg = 'Orange'
	})

	-- parameter
	vim.api.nvim_set_hl(0, "@lsp.type.parameter", {
		fg = 'LightYellow'
	})

	-- property
	vim.api.nvim_set_hl(0, "@lsp.type.property", {
		fg = '#7afffc'
	})

	-- Statement

	vim.api.nvim_set_hl(0, "Statement", {
		fg = '#4ee8fc'
	})

	-- type
	vim.api.nvim_set_hl(0, "@lsp.type.type", {
		fg = 'LightBlue'
	})
	vim.api.nvim_set_hl(0, "csType", {
		fg = '#ff8e9b'
	})

	-- variable
	vim.api.nvim_set_hl(0, "@lsp.type.variable", {
		fg = '#e8fc4f'
	})
	-- Braces
	vim.api.nvim_set_hl(0, "csBraces", {
		fg = 'Magenta'
	})
end

vim.api.nvim_create_user_command(
	'JonkSetSyntaxHl',
	function(opts)
		SetSyntaxHl();
	end,
	{})
