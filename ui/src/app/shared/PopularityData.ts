import { ChartData } from "./ChartData";

export class PopularityData {

    constructor(public chartData: ChartData[], public fullData: any) { }

    static fromRawData(rawData: any[]): PopularityData {
        let chartData = []
        let fullData = {}

        rawData.forEach(d => {
            chartData.push(new ChartData(d.entityName, d.gamesCount))
            fullData[d.entityName] = d
            fullData[d.entityName]['gamesInGroup'].sort((a, b) => (a.name > b.name) ? 1 : -1)
            fullData[d.entityName]['name'] = d.entityName
        })

        return new PopularityData(chartData, fullData)
    }
}