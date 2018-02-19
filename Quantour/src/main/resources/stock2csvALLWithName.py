# coding:utf-8
import requests
from bs4 import BeautifulSoup
import os
import csv

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.1708.400 QQBrowser/9.5.9635.400'
}


# parameter
# shareCode/year/season : num ,
def sharesCrawl(shareCode,year,season):
    shareCodeStr = str(shareCode)
    yearStr = str(year)
    seasonStr = str(season)
    url = 'http://quotes.money.163.com/trade/lsjysj_'+shareCodeStr+'.html?year='+yearStr+'&season='+seasonStr

    data = requests.get(url, headers=headers)
    soup = BeautifulSoup(data.text, 'html.parser')

    table = soup.findAll('table',{'class':'table_bg001'})[0]
    rows = table.findAll('tr')

    return rows[::-1]


def writeCSVWithName(shareCode,beginYear,endYear):
    shareCodeStr = str(shareCode)

    url = 'http://quotes.money.163.com/trade/lsjysj_' + shareCodeStr + '.html'
    data = requests.get(url, headers=headers)
    soup = BeautifulSoup(data.text, 'html.parser')
    name = soup.select('h1.name > a')[0].get_text()

    csvFile = open(shareCodeStr + name + '.csv', 'w')
    writer = csv.writer(csvFile)
    writer.writerow(('序号','日期','开盘价','最高价','最低价','收盘价','涨跌额','涨跌幅','成交量','成交金额','振幅','换手率'))

    try:
        for i in range(beginYear, endYear + 1):
            print (str(i) + ' is going')
            for j in range(1, 5):
                rows = sharesCrawl(shareCode,i,j)
                for row in rows:
                    csvRow = []
                    for cell in row.findAll('td'):
                        csvRow.append(cell.get_text().replace(',',''))
                    if csvRow != []:
                        writer.writerow(csvRow)
                print (str(i) + '年' + str(j)  + '季度is done')
    except:
        print ('----- Error-----')
    finally:
        csvFile.close()

writeCSVWithName(600018,2010,2016)
