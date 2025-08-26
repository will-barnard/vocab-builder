<template>
    <div>
        <h1 @click="$router.push({name: 'home'})" v-if="$store.state.token === ''">Vocab Builder</h1>
\        <p>This is a simple tool to help you as you build your vocabulary. Use the "Add Word" page to add words to your own custom list. The site will automatically pull a definition for the word. Then your list serves as a record of what words you have made effort to add to your vocabulary for your own personal reference.</p>
    </div>
</template>

<script>

export default {
}
</script>
<style>
    h1, h3 {
        text-align: center;
    }
    p {
        background-color: white;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        padding: 15px;
        margin: 20px;
    }
</style>