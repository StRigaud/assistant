package net.haesleinhuepf.clijx.incubator;

import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clijx.incubator.services.IncubatorPlugin;
import net.haesleinhuepf.clijx.incubator.utilities.IncubatorUtilities;
import net.haesleinhuepf.clijx.incubator.services.IncubatorPluginService;
import net.haesleinhuepf.clijx.incubator.services.MenuService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static net.haesleinhuepf.clijx.incubator.AbstractIncubatorPlugin.online_documentation_link;
import static net.haesleinhuepf.clijx.incubator.utilities.IncubatorUtilities.niceName;

public class GeneratePluginsConfig {
    public static void main(String[] args) throws IOException {
        IncubatorPluginService service = IncubatorPluginService.getInstance();

        ArrayList<CLIJMacroPlugin> supported_plugins = new ArrayList<>();

        ArrayList<String> menu_entries = new ArrayList<>();

        String[] order = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q"};

        int category_count = 0;
        for (String category : MenuService.getInstance().getCategories()) {

            category_count++;
            for (IncubatorPlugin plugin : MenuService.getInstance().getPluginsInCategory(category)) {
                supported_plugins.add(plugin.getCLIJMacroPlugin());
                String niceName = niceName(plugin.getName());
                String clijName = plugin.getCLIJMacroPlugin().getName();

                menu_entries.add(
                        "Plugins>ImageJ on GPU (CLIJx-Incubator)>" + order[category_count] + " " + category + ", " +
                                "\"" + niceName + " (experimental)_" + category_count + "\", " + plugin.getClass().getName() + "(\"" + clijName + "\")\n"
                );
            }
        }

        Collections.sort(menu_entries);
        {
            File outputTarget = new File("src/main/resources/plugins.config");
            FileWriter writer = new FileWriter(outputTarget);

            writer.write(new String(Files.readAllBytes(Paths.get("src/main/resources/manual_plugins.config"))));
            for (String entry : menu_entries) {
                writer.write(entry);
            }
            writer.close();
        }


        ArrayList<String> link_to_docs = new ArrayList<>();
        ArrayList<String> link_to_docs_label_measurements = new ArrayList<>();

        ArrayList<IncubatorPlugin> pluginsInCategory = MenuService.getInstance().getPluginsInCategory("Label>Measurement");


        HashMap<String, String> short_description = new HashMap<>();
        for (CLIJMacroPlugin plugin : supported_plugins) {
            String methodName = plugin.getName().replace("CLIJ2_", "").replace("CLIJx_", "");
            System.out.println(methodName);

            String description = "";
            if (plugin instanceof OffersDocumentation) {
                description = ((OffersDocumentation) plugin).getDescription().split("\n\n")[0];
            }

            String link = online_documentation_link + "_" + methodName;

            String new_entry = "* [" + niceName(methodName) + "](" + link + ")\n";
            if (!link_to_docs.contains(new_entry)) {
                link_to_docs.add(new_entry);
                short_description.put(new_entry, description);

                if (contains(pluginsInCategory, plugin)) {
                    link_to_docs_label_measurements.add(new_entry);
                }

            }
        }

        Collections.sort(link_to_docs);
        writeFunctionList(
                "../clij.github.io/incubator/reference.md",
                "## CLIIJx-incubator operations\n" +
                        "This is the list of currently supported [CLIJ2](https://clij.github.io/) and [CLIJx](https://clij.github.io/clijx) operations.\n\n" +
                        "Please note: CLIJx-Incubator is under development. Hence, this list is subject to change.\n\n",
                link_to_docs,
                short_description
        );

        Collections.sort(link_to_docs_label_measurements);
        writeFunctionList(
                "../clij.github.io/incubator/neighbor_analysis_generated.md",
                new String(Files.readAllBytes(Paths.get("../clij.github.io/incubator/neighbor_analysis.md"))),
                link_to_docs_label_measurements,
                short_description
        );

    }

    private static void writeFunctionList(String filename, String header, ArrayList<String> link_to_docs, HashMap<String, String> short_description) throws IOException {
        File outputTarget = new File(filename);
        FileWriter writer = new FileWriter(outputTarget);
        writer.append(header);

        int count = 0;
        for (String entry : link_to_docs) {
            writer.write(entry);
            writer.write(short_description.get(entry) + "\n\n");
            count++;
        }
        writer.append("\n\n" + count + " operations listed.\n\n\n" +
                "Back to [CLIncubator](https://clij.github.io/incubator)\n");

        writer.close();
    }

    private static boolean contains(ArrayList<IncubatorPlugin> list, CLIJMacroPlugin plugin) {
        for (IncubatorPlugin incubatorPlugin : list) {
            if (incubatorPlugin.getCLIJMacroPlugin().getClass() == plugin.getClass()) {
                return true;
            }
        }
        return false;
    }

}