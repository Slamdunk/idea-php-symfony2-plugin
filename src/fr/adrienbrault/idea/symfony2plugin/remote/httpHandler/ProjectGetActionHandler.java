package fr.adrienbrault.idea.symfony2plugin.remote.httpHandler;

import com.intellij.openapi.project.Project;
import com.sun.net.httpserver.HttpExchange;
import fr.adrienbrault.idea.symfony2plugin.remote.RemoteStorage;
import fr.adrienbrault.idea.symfony2plugin.remote.util.HttpExchangeUtil;
import fr.adrienbrault.idea.symfony2plugin.remote.util.RemoteUtil;

import java.io.IOException;

public class ProjectGetActionHandler {

    public void handleProject(Project project, HttpExchange xchg, String[] pathElements) throws IOException {

        if(pathElements.length > 2 && pathElements[2].equals("clear")) {
            RemoteStorage.removeInstance(project);
            HttpExchangeUtil.sendResponse(xchg, "cleared");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(project.getName()).append("\n");
        builder.append(project.getPresentableUrl()).append("\n");

        for(Class provider: RemoteUtil.getProviderClasses()) {
            if(RemoteStorage.getInstance(project).has(provider)) {
                builder.append("Storage: ").append(provider.toString()).append("\n");
            }
        }

        HttpExchangeUtil.sendResponse(xchg, builder);
    }

}