package lab4;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.function.Predicate;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.rule.RuleSetLoader;
import net.sourceforge.pmd.renderers.TextRenderer;
import net.sourceforge.pmd.reporting.Report;
import net.sourceforge.pmd.reporting.RuleViolation;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        root.setLevel(ch.qos.logback.classic.Level.ERROR);

        if (args.length < 1) {
            System.err.println("PMD analyzer: Lab4.exe <project directory>");
            System.exit(1);
        }

        var res = (args.length == 2)
            ? analyze(args[0], Integer.parseInt(args[1])) 
            : analyze(args[0]);
        int statusCode = 0;

        if (res != null) {
            statusCode = 1;
            System.out.println(res);
        }
        System.exit(statusCode);
    }

    public static String analyze(String path) throws Exception {
        return  analyze(path, null);
    }
    public static String analyze(String path, int priority) throws Exception {
        return  analyze(path, (v) -> v.getRule().getPriority().getPriority() <= priority);
    }
    
    private static String analyze(String path, Predicate<RuleViolation> filter) throws Exception {
        Path projectDir = Path.of(path).toAbsolutePath().normalize();

        PMDConfiguration config = new PMDConfiguration(LanguageRegistry.PMD);
        config.setThreads(0);

        try (PmdAnalysis pmd = PmdAnalysis.create(config)) {

            pmd.addRuleSets(new RuleSetLoader().getStandardRuleSets());
            
            pmd.files().addDirectory(projectDir, true);
            pmd.files().setCharset(StandardCharsets.UTF_8);

            Report report = pmd.performAnalysisAndCollectReport();

            if (report.getViolations().isEmpty()) {
                return null;
            } else {
                try (Writer writer = new StringWriter()) {

                    TextRenderer renderer = new TextRenderer();
                    renderer.setWriter(writer);
                    renderer.start();

                    if (filter != null) {
                        report = report.filterViolations(filter);
                    }
                    renderer.renderFileReport(report);

                    renderer.end();
                    renderer.flush();

                    return writer.toString();
                } 
            }
        }
    }
}
