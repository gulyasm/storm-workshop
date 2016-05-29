# Storm workshop

## Content
- [Sample data](#data)
    - [Attributes](#attributes)
- [Task 1 - Basic topology](#task-1)
- [Task 2 - Routing tuples](#task-2)
- [Task 3 - Groupings](#task-3)
- [Task 4 - Message guarantees and anchoring](#task-4)
- [Task 5 - Windowing](#task-5)


## Data
We will simulate a sensor data. One datapoint looks like the following:
```json
{
    "timestamp": 12341432423,
    "type": "data",
    "location": 1,
    "value": 12.32,
    "id:": "523EF00B-40A2-4D0D-A05F-D0B0F1D13E80",
    "sensorID": 12323

}
```
### Attributes
#### `timestamp`
It's a UNIX timestamp of the datapoint generated.
#### `type`
The type of the data. It can be the following:
- `temp`
- `ligth`
- `door`
- `alarm`
#### `location`
The location code where the sensor data comes from. The list of city codes can be found below.
```
0: Budapest (HU)
1: Berlin (DE)
2: London (GB)
3: Cairo (EG)
4: New York (US)
5: San Fransisco (US)
6: Toronto (CA)
7: Paris (FR)
8: Kaposvar (HU)
```
#### `sensorID`
A uniqe identifier(UUID) for the sensor this data comes from.
#### `value`
The value range and the meaning if this field is different for every type of sensor data.
- `temp`
Float, with Gaussian distribution. Mean: 15, Std: 20
- `light`
Float, with Gaussian distribution. Mean: 10, Std: 5
- `door`
Integer, the value is 1 or 0. 
    - 1: Door is opened
    - 0: Door is closed
- `alarm`
    - 1: Alarm activated by user
    - 0: Alarm deactivated by user
    - 2: Alarm is triggerd
    - 3: Alarm malfunctioning

#### `id`
A uniqe identifier(UUID) for the datapoint.


## Exercises
### Task 1
---
Basic topology
#### Descriptions
Create a topology where the spout generates sensor data every 100ms and a bolt that prints it as they come in.
#### Things we learn
- Spout
- Bolt
- TopologyBuilder 
- Groupings
- Start and stop a topology

### Task 2
---
Routing task
#### Description
Create a topology where different sensor data goes to different bolts.
#### Things we learn
- Multiple streams
- Routing tuples

### Task 3
---
Grouping task
#### Description
Create a counting bolt that receives the same City sensors.
#### Things we learn
- Groupings

### Task 4
---
Message failure and anchoring
#### Description
#### Things we learn
- Anchoring
- Ack tuples
- Fail tuples
- Receiving tuple at the spout

### Task 5
---
Average sensor data over the last 5 seconds
#### Description
Create a windowing bolt that averages sensor data in the last 5 seconds.
#### Things we learn
- Windowing
