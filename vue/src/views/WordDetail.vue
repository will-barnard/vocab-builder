<template>
    <LoadingWidget v-show="!show"/>
    <div v-show="show">
        <div v-if="word">
            <div class="single-row">
                <h1><strong>{{ word.word }}</strong></h1>
                <div class="spacer"></div>
                <div class="trash" @click="deleteWord">
                    <font-awesome-icon :icon="['fas', 'trash']"  />
                </div>
            </div>
            <div v-for="(meaning, index) in word.meanings" :key="meaning.meaningId" class="meaning">
                <p><strong>{{ index + 1 }}. {{ meaning.partOfSpeech }}</strong></p>
                <p>{{ meaning.phonetic }}</p>
                <ul>
                    <li v-for="def in meaning.definitions" :key="def.definition">{{ def.definition }}</li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
import WordService from "../services/WordService.js";
import LoadingWidget from "../components/LoadingWidget.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faTrash } from "@fortawesome/free-solid-svg-icons"; // Import icons

library.add(faTrash); // Add icons to the library

export default {
    components: {
        LoadingWidget,
        FontAwesomeIcon
    },
    data() {
        return {
            show: false,
            word: {}
        }
    },
    created() {
        WordService.getWord(this.$route.params.id)
            .then(response => {
                this.word = response.data;
                this.show = true;
            })
            .catch(error => {
                console.error("Error fetching word:", error);
            });
    },
    methods: {
        deleteWord() {
            if (confirm("Are you sure you want to delete this word?")) {
                WordService.deleteWord(this.word.wordId)
                    .then(() => {
                        this.$store.commit('DELETE_WORD', this.word.wordId);
                        this.$router.push({ name: 'list' });
                    })
                    .catch(error => {
                        console.error("Error deleting word:", error);
                    });
            }
        }
    }
}
</script>
<style>
.trash {
    cursor: pointer;
    padding: 5px;
    font-size: 1.5em;
    margin-right: 20px;
}
</style>