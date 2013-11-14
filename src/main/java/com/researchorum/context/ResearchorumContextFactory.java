package com.researchorum.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pbathala
 */
public class ResearchorumContextFactory {

    private static InheritableThreadLocal<ResearchorumContext> context = new InheritableThreadLocal<ResearchorumContext>();

    public static ResearchorumContext create(final HttpServletRequest request, final HttpServletResponse response) {
        ResearchorumContext ctx = new ResearchorumContext(request, response);
        setContext(ctx);

        return ctx;
    }

    public static ResearchorumContext getContext() {
        return context.get();
    }

    public static void setContext(final ResearchorumContext researchorumContext) {
        context.set(researchorumContext);
    }

    public  static void reset() {
        context.remove();
    }
}
