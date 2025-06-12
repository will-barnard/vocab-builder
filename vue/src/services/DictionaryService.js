import axios from 'axios';

export default {

  getDefinition(word) {
    return axios.get(`/dictionary?word=${(word)}`)
  }

}