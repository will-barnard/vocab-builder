<template>
    <div>
        <LoadingWidget v-show="!loaded"/>
        <div v-if="loaded">
            <input
                v-model="searchTerm"
                type="text"
                placeholder="Search words..."
                class="search-input"
            />
            <div v-for="word in filteredList" :key="word.wordId" class="generic-buttons">
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
            list: null,
            searchTerm: ''
        };
    },
    computed: {
        filteredList() {
            if (!this.list) return [];
            if (!this.searchTerm) return this.list;
            const term = this.searchTerm.toLowerCase();
            return this.list.filter(word => word.word.toLowerCase().includes(term));
        }
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
.search-input {
    width: 100%;
    padding: 8px;
    margin-bottom: 16px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
</style>