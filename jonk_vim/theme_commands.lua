function SetTsSynHl()
		vim.api.nvim_set_hl(0, "typescriptBraces", {
			fg = 'Orange'
		})
		vim.api.nvim_set_hl(0, "typescriptParens", {
			fg = 'Orange'
		})
end

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

		-- Fucntion/Method
		vim.api.nvim_set_hl(0, "@lsp.type.function", {
			fg = '#fffc7a'
		})
		vim.api.nvim_set_hl(0, "@function", {
			fg = '#fffc7a'
		})
		vim.api.nvim_set_hl(0, "@lsp.type.method", {
			fg = '#fffc7a'
		})
		vim.api.nvim_set_hl(0, "Function", {
			fg = '#fffc7a'
		})

		-- class
--		vim.api.nvim_set_hl(0, "@lsp.type.class", {
--			fg = 'Orange'
--		})
--		vim.api.nvim_set_hl(0, "@lsp.type.struct", {
--			fg = 'Orange'
--		})
		vim.api.nvim_set_hl(0, "Structure", {
			fg = '#9bff8e'
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

		-- type
		vim.api.nvim_set_hl(0, "@lsp.type.type", {
			fg = 'LightBlue'
		})
		vim.api.nvim_set_hl(0, "csType", {
			fg = '#ff8e9b'
		})

		-- variable
		vim.api.nvim_set_hl(0, "@lsp.type.variable", {
			fg = 'LightGreen'
		})
		-- Braces
		vim.api.nvim_set_hl(0, "csBraces", {
			fg = 'White'
		})
	-- code
end
vim.api.nvim_create_user_command(
	'JonkSetSyntaxHl',
	function(opts)
			SetSyntaxHl();
	end,
	{})
vim.api.nvim_create_user_command(
	'JonkTsSyntaxHl',
	function(opts)
			SetSyntaxHl();
			SetTsSynHl();
	end,
	{})
