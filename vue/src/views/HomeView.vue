<template>
  <div class="home">
    <div class="home-content" v-show="$store.state.token === ''">
      <h2>Welcome to Vocab Builder</h2>
      <p>This is a simple application to help you build and manage your vocabulary.</p>
      <div class="generic-buttons">
        <p @click="$router.push({name: 'login'})">Login</p>
        <p @click="$router.push({name: 'register'})">Create Account</p>
        <p @click="$router.push({name: 'about'})">Learn More</p>
      </div>
    </div>
    <div class="home-content" v-show="$store.state.token != ''">
      <h2>Welcome Back!</h2>
      <p>You are logged in as {{ $store.state.user.username }}.</p>
      <div class="generic-buttons">
        <p @click="$router.push({name: 'list'})">My List</p>
        <p @click="$router.push({name: 'logout'})">Logout</p>
        <p @click="$router.push({name: 'about'})">Learn More</p>
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

</style>