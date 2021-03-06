/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.service.treenode;

import org.jd.gui.api.API;
import org.jd.gui.api.feature.ContainerEntryGettable;
import org.jd.gui.api.feature.PageCreator;
import org.jd.gui.api.feature.UriGettable;
import org.jd.gui.api.model.Container;
import org.jd.gui.view.component.OneTypeReferencePerLinePage;
import org.jd.gui.view.data.TreeNodeBean;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.regex.Pattern;

public class MetainfServiceFileTreeNodeFactoryProvider extends FileTreeNodeFactoryProvider {
    protected static final ImageIcon ICON = new ImageIcon(TextFileTreeNodeFactoryProvider.class.getClassLoader().getResource("org/jd/gui/images/ascii_obj.png"));

    @Override public String[] getSelectors() { return appendSelectors("*:file:*"); }

    @Override
    public Pattern getPathPattern() {
        if (externalPathPattern == null) {
            return Pattern.compile("META-INF\\/services\\/[^\\/]+");
        } else {
            return externalPathPattern;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends DefaultMutableTreeNode & ContainerEntryGettable & UriGettable> T make(API api, Container.Entry entry) {
        int lastSlashIndex = entry.getPath().lastIndexOf("/");
        String name = entry.getPath().substring(lastSlashIndex+1);
        return (T)new TreeNode(entry, new TreeNodeBean(name, "Location: " + entry.getUri().getPath(), ICON));
    }

    protected static class TreeNode extends FileTreeNodeFactoryProvider.TreeNode implements PageCreator {
        public TreeNode(Container.Entry entry, Object userObject) {
            super(entry, userObject);
        }

        // --- PageCreator --- //
        @Override
        @SuppressWarnings("unchecked")
        public <T extends JComponent & UriGettable> T createPage(API api) {
            return (T)new OneTypeReferencePerLinePage(api, entry);
        }
    }
}
