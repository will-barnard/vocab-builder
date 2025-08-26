<template>
  <div class="home">
    <div class="home-content" v-show="$store.state.token === ''">
      <h2>Welcome to Vocab Builder</h2>
      <div class="generic-buttons">
        <p @click="$router.push({name: 'register'})">Create Account</p>
        <p @click="$router.push({name: 'login'})">Login</p>
      </div>
    </div>
    <div class="home-content" v-show="$store.state.token != ''">
      <div class="generic-buttons">
        <p @click="$router.push({name: 'add'})">Add Word</p>
        <p @click="$router.push({name: 'list'})">My List</p>
      </div>
      
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {};
  },
  methods: {}
};
</script>

<style scoped>
.home-content {
  text-align: center;
  margin-top: 20px;
  padding: 20px;
  background-color: var(--form-background-color);
  border: 1px solid var(--form-border-color);
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.home-footer {
  margin-top: 30px;
  width: 100%;
  text-align: center;
  color: var(--text-color, #333);
  font-size: 1rem;
}
.home-footer .footer-link {
  display: inline-block;
  margin-left: auto;
  margin-right: auto;
}
.footer-link {
  cursor: pointer;
  color: var(--primary-color, #007bff);
  text-decoration: underline;
  margin: 0 5px;
}
.footer-link:hover {
  text-decoration: none;
}

</style>