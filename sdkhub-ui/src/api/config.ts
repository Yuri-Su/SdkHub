import axios from 'axios'
import type { Config } from '@/types/config'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

export const getConfig = async (): Promise<Config> => {
  const response = await api.get<Config>('/config')
  return response.data
}

export const updateConfig = async (config: Config): Promise<Config> => {
  const response = await api.post<Config>('/config', config)
  return response.data
}
