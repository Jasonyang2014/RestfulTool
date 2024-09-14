/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: WithLibraryAction
  Author:   ZhangYuanSheng
  Date:     2020/8/21 17:20
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.github.restful.tool.actions;

import com.github.restful.tool.beans.PropertiesKey;
import com.github.restful.tool.utils.Bundle;
import com.github.restful.tool.view.window.RestfulToolWindowFactory;
import com.github.restful.tool.view.window.frame.RightToolWindow;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ZhangYuanSheng
 * @version 1.0
 * com.intellij.diagnostic.PluginException: `ActionUpdateThread.OLD_EDT` is deprecated and going to be removed soon. 'com.github.restful.tool.actions.WithLibraryAction' must override `getActionUpdateThread()` and chose EDT or BGT. See ActionUpdateThread javadoc. [Plugin: cn.cloud.auto.restful.tool]
 * 	at com.intellij.diagnostic.PluginProblemReporterImpl.createPluginExceptionByClass(PluginProblemReporterImpl.java:23)
 * 	at com.intellij.diagnostic.PluginException.createByClass(PluginException.java:90)
 * 	at com.intellij.diagnostic.PluginException.reportDeprecatedUsage(PluginException.java:125)
 * 	at com.intellij.openapi.actionSystem.ActionUpdateThreadAware.getActionUpdateThread(ActionUpdateThreadAware.java:21)
 * 	at com.intellij.openapi.actionSystem.AnAction.getActionUpdateThread(AnAction.java:201)
 */
public class WithLibraryAction extends ToggleAction implements DumbAware {

    private RightToolWindow toolWindow;

    public WithLibraryAction() {
        getTemplatePresentation().setText(Bundle.getString("action.WithLibrary.text"));
        getTemplatePresentation().setIcon(AllIcons.ObjectBrowser.ShowLibraryContents);
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent e) {
        return PropertiesKey.scanServiceWithLibrary(e.getRequiredData(CommonDataKeys.PROJECT));
    }

    @Override
    public void setSelected(@NotNull AnActionEvent e, boolean state) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }
        PropertiesKey.scanServiceWithLibrary(project, state);
        if (getToolWindow(project) != null) {
            toolWindow.refreshRequestTree();
        }
    }

    private RightToolWindow getToolWindow(@Nullable Project project) {
        if (toolWindow != null) {
            return toolWindow;
        }
        return (toolWindow = RestfulToolWindowFactory.getToolWindow(project));
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
