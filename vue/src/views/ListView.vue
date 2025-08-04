<template>
    <div>
        <LoadingWidget v-show="!loaded"/>
        <div v-if="loaded">
            <div v-for="word in $store.state.vocabList" :key="word.wordId" class="generic-buttons">
                <p @click="$router.push({name: 'word-detail', params: {id: word.wordId}})">{{ word.word }}</p>
            </div>
        </div>
       
        <div v-if="$store.state.vocabList.length === 0 && $store.state.vocabListLoaded" class="empty-list">
            <p>You haven't added any words yet</p>
            <p>Navigate to "Add Word" at the top to get started</p>
        </div>
    </div>
</template>

<script>
import LoadingWidget from "../components/LoadingWidget.vue";

export default {
    components: {
        LoadingWidget
    },
    data() {
        return {
            loaded: false,
            list: null
        };
    },
    created() {
        setTimeout(() => {
            if (this.$store.state.vocabListLoaded) {
                this.list = this.$store.state.vocabList;
                this.loaded = true;
            }
        }, 500);
    }
}
</script>
<style>
    
</style>