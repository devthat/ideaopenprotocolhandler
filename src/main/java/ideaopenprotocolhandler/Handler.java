package ideaopenprotocolhandler;

import java.awt.Desktop;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ui.UIUtil;

public class Handler implements ApplicationComponent {

  private static final Logger LOG = Logger.getInstance(Handler.class);
  private static final Pattern pattern = Pattern.compile("^idea://open\\?file=([^&]+)(?:&line=(\\d+))?$");

  @Override
  public void initComponent() {
    if (!Desktop.isDesktopSupported()) {
      LOG.warn("Desktop is not supported on this platform");
      return;
    }
    Desktop desktop = Desktop.getDesktop();
    if (!desktop.isSupported(Desktop.Action.APP_OPEN_URI)) {
      LOG.warn("APP_OPEN_URI action not supported on this platform");
      return;
    }
    desktop.setOpenURIHandler(e -> {
      URI uri = e.getURI();
      if ("idea".equals(uri.getScheme())) {
        UIUtil.invokeLaterIfNeeded(() -> handleUrl(uri.toString()));
      }
    });
  }

  public void handleUrl(String url) {
    Matcher matcher = pattern.matcher(url);

    if (matcher.matches()) {
      var filePath = matcher.group(1);
      int lineNumber = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 1;
      openFileAtLine(filePath, lineNumber);
    }
  }

  private void openFileAtLine(String fileName, int lineNumber) {
    LOG.warn("Opening '" + fileName + "' on line '" + lineNumber + "'");
    var projects = ProjectManager.getInstance().getOpenProjects();
    if (projects.length == 0) {
      return;
    }
    var currentProject = projects[0];

    var scope = GlobalSearchScope.projectScope(currentProject);
    var foundFile = FilenameIndex.getVirtualFilesByName(fileName, scope).stream().findFirst();

    if (foundFile.isEmpty()) {
      LOG.warn("File not found: " + fileName);
      return;
    }
    ApplicationManager.getApplication().invokeLater(() -> {
      var fileEditorManager = FileEditorManager.getInstance(currentProject);
      fileEditorManager.openFile(foundFile.get(), true);
      var editor = fileEditorManager.getSelectedTextEditor();
      if (editor != null) {
        editor.getCaretModel().moveToOffset(editor.getDocument().getLineStartOffset(lineNumber - 1));
        editor.getScrollingModel().scrollToCaret(ScrollType.CENTER);
      }
    });
  }
}
