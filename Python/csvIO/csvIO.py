import csv


def getLinesFromCsvFile(filePath: str) -> list:
    lines = []
    with open(filePath, 'r') as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            lines.append(row)
    return lines


def writeToCsvFile(filePath: str, lines: list):
    with open(filePath, 'w') as csvfile:
        writer = csv.writer(csvfile, delimiter=',', lineterminator='\n')
        for line in lines:
            writer.writerow(line)
