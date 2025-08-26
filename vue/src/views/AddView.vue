<template>
    <div class="form-container">
        <LoadingWidget v-show="push" />
        <form v-on:submit.prevent="add" v-on:keydown.enter.prevent v-show="!push">
        <h2>Add Word</h2>
        <!-- <div role="alert" v-if="registrationErrors">
            {{ registrationErrorMsg }}
        </div> -->
        <div class="form-input-group form-element">
            <div class="top-element">
                <input type="text" id="word" v-model="word.word" required autofocus />
                <button type="button" @click="getDefinition">Get Definition</button>
            </div>
            <div v-if="dictResult">
                <div class="single-row">
                    <h1>{{ dictResult.word }}</h1>
                    <div class="spacer"></div>
                    <button type="submit" class="submit-button">Add to List</button>
                </div>
                
                <div v-for="meaning in dictResult.meanings" :key="meaning.meaningId" class="meaning">
                    <p><strong>{{  meaning.meaningId + 1 }}. {{ meaning.partOfSpeech }}</strong></p>
                    <p>{{ meaning.phonetic }}</p>
                    <ul>
                        <li v-for="def in meaning.definitions" :key="def.definition">{{ def.definition }}</li>
                    </ul>
                </div>
            </div>
        </div>
        

        <!-- <div class="bottom-buttons">
            <button type="submit" class="submit-button">Add to List</button>
            <button type="button" @click="$router.push({name: 'add-manual'})">Add Manually</button>
        </div> -->
        </form>
    
    </div>
</template>

<script>
import WordService from '../services/WordService';
import DictionaryService from '../services/DictionaryService';
import LoadingWidget from '../components/LoadingWidget.vue';

export default {
    components: {
        LoadingWidget
    },
    data() {
        return {
            word: {},
            dictResult: null,
            push: false
        }
    },
    methods: {
        add() {
            if (this.dictResult) {
                this.word.meanings = this.dictResult.meanings;
                this.word.word = this.dictResult.word;
            }
            this.push = true;
            WordService.createWord(this.word)
                .then(response => {
                    this.$store.commit('ADD_WORD', response.data);
                    this.$router.push({ name: 'list' });
                })
                .catch(error => {
                    console.error("Error adding word:", error);
                });
        },
        getDefinition() {
            DictionaryService.getDefinition(this.word.word)
                .then(response => {
                    this.dictResult = response.data;
                    // this.word.definition = this.dictResult.definition;
                    // this.word.example = this.dictResult.example;
                })
                .catch(error => {
                    console.error("Error fetching definition:", error);
                });
        }
    }
}
</script>
<style scoped>
    p {
        margin: 0px;
    }
    .form-element {
        margin-bottom: 1rem;
    }
    .top-element {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
    }
    input {
        flex-grow: 1;
        margin-right: 10px;
    }
    .bottom-buttons {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        margin-top: 20px;
    }

</style>