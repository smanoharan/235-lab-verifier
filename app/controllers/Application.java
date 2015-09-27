package controllers;

import models.Lab42RunResult;
import models.ResultsList;
import models.RunResult;
import play.mvc.*;
import views.html.*;
import views.html.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The main controller for handling uploaded Turing machines.
 *
 * @author Siva
 * @author nathaniel
 */
public class Application extends Controller
{
    public Result index()
    {
        return ok(index.render(null));
    }

    public Result checkAll()
    {
        Http.MultipartFormData body = request().body().asMultipartFormData();

        List<Http.MultipartFormData.FilePart> files = body.getFiles();

        if (files.size() == 0)
        {
            return ok(index.render("Please upload at least one file"));
        }

        List<ResultsList<RunResult>> allResults = new ArrayList<ResultsList<RunResult>>();
        ResultsList<Lab42RunResult> lab42results = null;

        // test each uploaded machine:
        for (Http.MultipartFormData.FilePart f : files)
        {
            if (f.getFile() != null)
            {
                allResults.add(toResult(f.getFile(), f.getKey()));

                if (f.getKey().equals("4.1"))
                {
                    lab42results = this.testLab42(f.getFile());
                }
            }
        }

        return ok(abstractResults.render(allResults, lab42results));
    }

    // for running labs1.1 to 4.1
    private ResultsList<RunResult> toResult(File f, String type)
    {
        String machinePath = f.getPath();
        String testPath = "app/inputs/t" + type + ".txt";
        return Tester.testMachine(machinePath, testPath, type, new NormalTestRunner());
    }

    // for running lab 4.2
    private ResultsList<Lab42RunResult> testLab42(File f)
    {
        String machinePath = f.getPath();
        String testPath = "app/inputs/t4.2.txt";
        return Tester.testMachine(machinePath, testPath, "4.2", new Lab42TestRunner());
    }
}
