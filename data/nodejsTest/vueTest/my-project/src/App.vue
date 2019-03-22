<template>
  <div id="app">
    <img src="./assets/logo.png">
    <router-link to="/test">go to test</router-link>
    <router-view/>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld'
import test1 from './components/Test1'
export default {
  name: 'App',
  components: {
    HelloWorld,
    test1
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
