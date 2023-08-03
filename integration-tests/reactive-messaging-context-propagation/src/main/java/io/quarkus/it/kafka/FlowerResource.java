package io.quarkus.it.kafka;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.context.ThreadContext;
import org.eclipse.microprofile.reactive.messaging.Channel;

import io.quarkus.logging.Log;
import io.smallrye.context.api.CurrentThreadContext;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.vertx.core.Context;
import io.vertx.core.Vertx;

@Path("/flowers")
public class FlowerResource {

    @Channel("flower")
    MutinyEmitter<String> emitter;

    @Channel("flower-blocking")
    MutinyEmitter<String> emitterBlocking;

    @Channel("flower-blocking-named")
    MutinyEmitter<String> emitterBlockingNamed;

    @Channel("flower-virtual-thread")
    MutinyEmitter<String> emitterVT;

    @Inject
    RequestBean reqBean;

    @CurrentThreadContext(cleared = ThreadContext.CDI)
    void emitWithoutContext(MutinyEmitter<String> emitter, String body) {
        emitter.sendAndAwait(body);
    }

    @CurrentThreadContext(cleared = ThreadContext.CDI)
    Uni<Void> emitWithoutContextUni(MutinyEmitter<String> emitter, String body) {
        return emitter.send(body);
    }

    @POST
    @Path("/uni")
    @Consumes(MediaType.TEXT_PLAIN)
    public Uni<Void> uniEventLoop(String body) {
        Context ctx = Vertx.currentContext();
        Log.info(ctx + "[" + ctx.getClass() + "]");
        Log.infof("bean: %s, id: %s", reqBean, reqBean.getId());
        reqBean.setName(body != null ? body.toUpperCase() : body);
        return emitWithoutContextUni(emitter, body);
    }

    @POST
    @Path("/uni/blocking")
    @Consumes(MediaType.TEXT_PLAIN)
    public Uni<Void> uniBlocking(String body) {
        Context ctx = Vertx.currentContext();
        Log.info(ctx + "[" + ctx.getClass() + "]");
        Log.infof("bean: %s, id: %s", reqBean, reqBean.getId());
        reqBean.setName(body != null ? body.toUpperCase() : body);
        return emitWithoutContextUni(emitterBlocking, body);
    }

    @POST
    @Path("/uni/blocking-named")
    @Consumes(MediaType.TEXT_PLAIN)
    public Uni<Void> uniBlockingNamed(String body) {
        Context ctx = Vertx.currentContext();
        Log.info(ctx + "[" + ctx.getClass() + "]");
        Log.infof("bean: %s, id: %s", reqBean, reqBean.getId());
        reqBean.setName(body != null ? body.toUpperCase() : body);
        return emitWithoutContextUni(emitterBlockingNamed, body);
    }

    @POST
    @Path("/uni/virtual-thread")
    @Consumes(MediaType.TEXT_PLAIN)
    public Uni<Void> uniVT(String body) {
        Context ctx = Vertx.currentContext();
        Log.info(ctx + "[" + ctx.getClass() + "]");
        Log.infof("bean: %s, id: %s", reqBean, reqBean.getId());
        reqBean.setName(body != null ? body.toUpperCase() : body);
        return emitWithoutContextUni(emitterVT, body);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.TEXT_PLAIN)
    public void eventLoop(String body) {
        Context ctx = Vertx.currentContext();
        Log.info(ctx + "[" + ctx.getClass() + "]");
        Log.infof("bean: %s, id: %s", reqBean, reqBean.getId());
        reqBean.setName(body != null ? body.toUpperCase() : body);
        emitWithoutContext(emitter, body);
    }

    @POST
    @Path("/blocking")
    @Consumes(MediaType.TEXT_PLAIN)
    public void blocking(String body) {
        Context ctx = Vertx.currentContext();
        Log.info(ctx + "[" + ctx.getClass() + "]");
        Log.infof("bean: %s, id: %s", reqBean, reqBean.getId());
        reqBean.setName(body != null ? body.toUpperCase() : body);
        emitWithoutContext(emitterBlocking, body);
    }

    @POST
    @Path("/blocking-named")
    @Consumes(MediaType.TEXT_PLAIN)
    public void blockingNamed(String body) {
        Context ctx = Vertx.currentContext();
        Log.info(ctx + "[" + ctx.getClass() + "]");
        Log.infof("bean: %s, id: %s", reqBean, reqBean.getId());
        reqBean.setName(body != null ? body.toUpperCase() : body);
        emitWithoutContext(emitterBlockingNamed, body);
    }

    @POST
    @Path("/virtual-thread")
    @Consumes(MediaType.TEXT_PLAIN)
    public void vt(String body) {
        Context ctx = Vertx.currentContext();
        Log.info(ctx + "[" + ctx.getClass() + "]");
        Log.infof("bean: %s, id: %s", reqBean, reqBean.getId());
        reqBean.setName(body != null ? body.toUpperCase() : body);
        emitWithoutContext(emitterVT, body);
    }

}
