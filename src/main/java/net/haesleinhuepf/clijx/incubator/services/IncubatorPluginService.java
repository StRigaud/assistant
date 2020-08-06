package net.haesleinhuepf.clijx.incubator.services;

import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.imagej.ImageJService;
import org.scijava.Context;
import org.scijava.InstantiableException;
import org.scijava.plugin.AbstractPTService;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginInfo;
import org.scijava.service.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

@Plugin(type = Service.class)
public class IncubatorPluginService extends AbstractPTService<IncubatorPlugin> implements ImageJService {

    private ArrayList<PluginInfo<IncubatorPlugin>> suggestedPlugins = new ArrayList<>();
    private HashMap<String, PluginInfo<IncubatorPlugin>> namedPlugins = new HashMap<>();
    //private HashMap<Class, ArrayList<Class>> suggestedNextSteps = new HashMap<>();

    private ArrayList<IncubatorPlugin> plugins = new ArrayList<>();


    @Override
    public void initialize() {
        //initializeService();
    }

    private boolean initialized = false;
    private synchronized void initializeService() {
        if (initialized) {
            return;
        }

        for (final PluginInfo<IncubatorPlugin> info : getPlugins()) {
            /*String name = info.getName();
            if (name == null || name.isEmpty()) {
                name = info.getClassName();
            }*/

            //System.out.println(info);
            //System.out.println(info.getName());

            IncubatorPlugin current = pluginService().createInstance(info);
            plugins.add(current);

            if (current != null) {
                String name = current.getClass().getSimpleName();
                String[] temp = current.getClass().getPackage().getName().split("\\.");
                String packageName = temp[temp.length - 1];

                suggestedPlugins.add(info);
                namedPlugins.put(name, info);

                //ArrayList<Class> services = new ArrayList<>();

                //System.out.println("Initial services for " + name);
                //for (Class suggestion : current.suggestedNextSteps()) {
                    //System.out.println("  " + suggestion.getSimpleName());
                //    services.add(suggestion);
                //}
                //suggestedNextSteps.put(current.getClass(), services);
            }
        }

        /*
        for (PluginInfo<IncubatorPlugin> info : suggestedPlugins) {
            IncubatorPlugin plugin = pluginService().createInstance(info);
            for (Class previousStep : plugin.suggestedPreviousSteps()) {
                if (suggestedNextSteps.get(previousStep) != null) {
                    if (!suggestedNextSteps.get(previousStep).contains(plugin.getClass())) {
                        suggestedNextSteps.get(previousStep).add(plugin.getClass());
                    }
                }
            }
        }
*/

        initialized = true;
    }


    @Override
    public Class<IncubatorPlugin> getPluginType() {
        return IncubatorPlugin.class;
    }
/*
    public Class[] getSuggestedNextStepsFor(IncubatorPlugin current) {
        initializeService();

        ArrayList<Class> list = suggestedNextSteps.get(current.getClass());
        if (list != null) {
            Class[] classes = new Class[list.size()];
            list.toArray(classes);
            return classes;
        } else {
            return new Class[0];
        }
    }*/

    static IncubatorPluginService instance = null;
    public static IncubatorPluginService getInstance() {
        if (instance == null) {
            instance = new Context(IncubatorPluginService.class).getService(IncubatorPluginService.class);
        }
        return instance;
    }

    public ArrayList<String> getNames() {
        initializeService();
        Set set = namedPlugins.keySet();
        ArrayList<String> list = new ArrayList<>(set);
        Collections.sort(list);
        return list;
    }

    public IncubatorPlugin getPluginByName(String name) {
        initializeService();
        try {
            return namedPlugins.get(name).createInstance();
        } catch (InstantiableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class getIncubatorPluginClassFromCLIJ2Plugin(CLIJMacroPlugin plugin) {
        initializeService();
        for (IncubatorPlugin incubatorPlugin : plugins) {
            if (incubatorPlugin.canManage(plugin)) {
                return incubatorPlugin.getClass();
            }
        }
        return null;
    }
}