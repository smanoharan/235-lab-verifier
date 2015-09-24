package controllers;

import models.ResultsList;
import models.RunResult;
import play.mvc.Controller;

import play.mvc.*;

import views.html.*;

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
    public Result index() {
        return ok(index.render(null));
    }

    public Result checkAll() {
        Http.MultipartFormData body = request().body().asMultipartFormData();

        List<Http.MultipartFormData.FilePart> files = body.getFiles();

        if (files.size() == 0) {
            return ok(index.render("Please upload at least one file"));
        }

        List<ResultsList<RunResult>> allResults = new ArrayList<ResultsList<RunResult>>();

        // test each uploaded machine:
        for (Http.MultipartFormData.FilePart f : files) {
            if (f.getFile() != null) {
                System.out.println("checking " + f.getFile().getPath());
                allResults.add(toResult(f.getFile(), f.getKey()));
            }
        }

        return ok(abstractResults.render(allResults));
    }

    // for running labs1.1 to 4.1
    private ResultsList<RunResult> toResult(File f, String type) {
        String machinePath = f.getPath();
        String testPath = "app/inputs/" + type + ".txt";
        return Tester.testMachine(machinePath, testPath, type, new NormalTestRunner());
    }
}
