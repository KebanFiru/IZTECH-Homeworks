package util;

import model.tools.SpecialTool;

public interface ToolUser <T extends SpecialTool>{

    public void useTool(T tool, int row, int column);
}
