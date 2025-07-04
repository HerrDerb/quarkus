package io.quarkus.kafka.client.deployment.devui;

import org.jboss.logging.Logger;

import io.quarkus.deployment.IsLocalDevelopment;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.devui.spi.JsonRPCProvidersBuildItem;
import io.quarkus.devui.spi.page.CardPageBuildItem;
import io.quarkus.devui.spi.page.Page;
import io.quarkus.kafka.client.runtime.dev.ui.KafkaJsonRPCService;

/**
 * Kafka Dev UI (v2)
 */
public class KafkaDevUIProcessor {

    private static final Logger log = Logger.getLogger(KafkaDevUIProcessor.class);

    @BuildStep(onlyIf = IsLocalDevelopment.class)
    public CardPageBuildItem pages() {
        CardPageBuildItem cardPageBuildItem = new CardPageBuildItem();
        cardPageBuildItem.setLogo("kafka_dark.png", "kafka_light.png");
        cardPageBuildItem.addLibraryVersion("org.apache.kafka", "kafka-clients", "Apache Kafka", "https://kafka.apache.org/");

        cardPageBuildItem.addPage(Page.webComponentPageBuilder()
                .icon("font-awesome-solid:folder-tree")
                .componentLink("qwc-kafka-topics.js")
                .title("Topics"));
        //  TODO: Implement this. This is also not implemented in the old Dev UI
        //        cardPageBuildItem.addPage(Page.webComponentPageBuilder()
        //                .icon("font-awesome-solid:file-circle-check")
        //                .componentLink("qwc-kafka-schema-registry.js")
        //                .title("Schema registry"));

        cardPageBuildItem.addPage(Page.webComponentPageBuilder()
                .icon("font-awesome-solid:inbox")
                .componentLink("qwc-kafka-consumer-groups.js")
                .title("Consumer groups"));

        cardPageBuildItem.addPage(Page.webComponentPageBuilder()
                .icon("font-awesome-solid:key")
                .componentLink("qwc-kafka-access-control-list.js")
                .title("Access control list"));

        cardPageBuildItem.addPage(Page.webComponentPageBuilder()
                .icon("font-awesome-solid:circle-nodes")
                .componentLink("qwc-kafka-nodes.js")
                .title("Nodes"));

        return cardPageBuildItem;
    }

    @BuildStep(onlyIf = IsLocalDevelopment.class)
    JsonRPCProvidersBuildItem createJsonRPCService() {
        return new JsonRPCProvidersBuildItem(KafkaJsonRPCService.class);
    }
}
