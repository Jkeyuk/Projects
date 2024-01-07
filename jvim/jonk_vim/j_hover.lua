function Jhover()
    vim.lsp.buf.definition { on_list = function(opts)
        for index, value in ipairs(opts.items) do
            print(index, ' ', value)
            for i2, v2 in pairs(value) do
                print(i2, ' ', v2)
                print(value.text)
            end
        end
    end }
end

