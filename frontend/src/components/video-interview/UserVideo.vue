<template>
    <div v-if="streamManager" :class="['video-wrapper', { 'speaking': isSpeaking }]">
      <ov-video :stream-manager="streamManager"/>
      <div>
        <p class="client-name">{{ clientData }}</p>
        <button v-if="isSubscriber" @click="handleToggleAudio" class="sound-off">
          <img :src="audioMuted ? require('@/assets/img/video-interview/sound-on-white.png') : require('@/assets/img/video-interview/sound-off-white.png')" alt="sound">
        </button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, onUnmounted, computed, defineEmits, defineProps } from 'vue';
  import OvVideo from './OvVideo';

  const props = defineProps({
    streamManager: Object, 
    isSubscriber: Boolean,
    audioMuted: {type: Boolean, default: true},
  });
  const emit = defineEmits(['handle-toggle-audio']);

  const isSpeaking = ref(false);
  
  onMounted(() => {
    if (props.streamManager) {
      const { streamManager } = props;
      streamManager.on('publisherStartSpeaking', handleStartSpeaking);
      streamManager.on('publisherStopSpeaking', handleStopSpeaking);
      streamManager.on('streamAudioVolumeChange', handleAudioVolumeChange);
      streamManager.updatePublisherSpeakingEventsOptions({ interval: 100, threshold: -50 });
    }
  });
  
  onUnmounted(() => {
    if (props.streamManager) {
      const { streamManager } = props;
      streamManager.off('publisherStartSpeaking', handleStartSpeaking);
      streamManager.off('publisherStopSpeaking', handleStopSpeaking);
      streamManager.off('streamAudioVolumeChange', handleAudioVolumeChange);
    }
  });
  
  const clientData = computed(() => {
    const { connection } = props.streamManager.stream;
    const { clientData } = JSON.parse(connection.data);
    return clientData || '면접관';
  });
  
  const handleToggleAudio = () => {
    if (props.isSubscriber && props.streamManager) {
      const { stream } = props.streamManager;
      if (stream && stream.connection) {
        const { connection } = stream;
        emit('handle-toggle-audio', connection);
      }
    }
  };
  
  const handleStartSpeaking = (event) => {
    if (event.connection.connectionId === props.streamManager.stream.connection.connectionId) {
      isSpeaking.value = true;
    }
  };
  
  const handleStopSpeaking = (event) => {
    if (event.connection.connectionId === props.streamManager.stream.connection.connectionId) {
      isSpeaking.value = false;
    }
  };
  
  const handleAudioVolumeChange = (event) => {
    isSpeaking.value = event.value.newValue > -70;
  };
  </script>
  
  <style scoped>
  .video-wrapper {
    padding: 10px;
    position: relative;
    height: 100%;
    transition: border 0.3s ease;
    border-radius: 5px; /* 모서리를 둥글게 */
  }
  
  .speaking {
    background-color: rgba(2, 249, 59, 0.5);
  }
  
  .ov-video {
    width: 100%;
    height: 100%;
  }
  
  .client-name {
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
    background-color: white;
    color: black;
    padding: 10px;
    border-radius: 5px;
    font-size: 18px;
    text-align: center;
  }
  
  .sound-off {
    position: absolute;
    bottom: 29px;
    left: 60%;
    transform: translateX(-50%);
    background-color: white;
    color: white;
    border: none;
    width: 40px;
    height: 43px;
    display: flex;
    border-radius: 5px;
    justify-content: center;
    align-items: center;
  }
  
  .sound-off img {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
  </style>
  