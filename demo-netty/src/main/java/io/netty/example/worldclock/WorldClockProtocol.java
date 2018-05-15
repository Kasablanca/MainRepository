package io.netty.example.worldclock;

@SuppressWarnings("all")
public class WorldClockProtocol {

	private WorldClockProtocol() {}

	public static void registerAllExtensions(
			com.google.protobuf.ExtensionRegistry registry) {
	}
	/**
	 * Protobuf enum {@code Continent}
	 */
	public enum Continent implements com.google.protobuf.ProtocolMessageEnum {
		/**
		 * <code>AFRICA = 0;</code>
		 */
		AFRICA(0, 0),
		/**
		 * <code>AMERICA = 1;</code>
		 */
		AMERICA(1, 1),
		/**
		 * <code>ANTARCTICA = 2;</code>
		 */
		ANTARCTICA(2, 2),
		/**
		 * <code>ARCTIC = 3;</code>
		 */
		ARCTIC(3, 3),
		/**
		 * <code>ASIA = 4;</code>
		 */
		ASIA(4, 4),
		/**
		 * <code>ATLANTIC = 5;</code>
		 */
		ATLANTIC(5, 5),
		/**
		 * <code>AUSTRALIA = 6;</code>
		 */
		AUSTRALIA(6, 6),
		/**
		 * <code>EUROPE = 7;</code>
		 */
		EUROPE(7, 7),
		/**
		 * <code>INDIAN = 8;</code>
		 */
		INDIAN(8, 8),
		/**
		 * <code>MIDEAST = 9;</code>
		 */
		MIDEAST(9, 9),
		/**
		 * <code>PACIFIC = 10;</code>
		 */
		PACIFIC(10, 10),
		;
		/**
		 * <code>AFRICA = 0;</code>
		 */
		public static final int AFRICA_VALUE = 0;
		/**
		 * <code>AMERICA = 1;</code>
		 */
		public static final int AMERICA_VALUE = 1;
		/**
		 * <code>ANTARCTICA = 2;</code>
		 */
		public static final int ANTARCTICA_VALUE = 2;
		/**
		 * <code>ARCTIC = 3;</code>
		 */
		public static final int ARCTIC_VALUE = 3;
		/**
		 * <code>ASIA = 4;</code>
		 */
		public static final int ASIA_VALUE = 4;
		/**
		 * <code>ATLANTIC = 5;</code>
		 */
		public static final int ATLANTIC_VALUE = 5;
		/**
		 * <code>AUSTRALIA = 6;</code>
		 */
		public static final int AUSTRALIA_VALUE = 6;
		/**
		 * <code>EUROPE = 7;</code>
		 */
		public static final int EUROPE_VALUE = 7;
		/**
		 * <code>INDIAN = 8;</code>
		 */
		public static final int INDIAN_VALUE = 8;
		/**
		 * <code>MIDEAST = 9;</code>
		 */
		public static final int MIDEAST_VALUE = 9;
		/**
		 * <code>PACIFIC = 10;</code>
		 */
		public static final int PACIFIC_VALUE = 10;
		public final int getNumber() { return value; }
		public static Continent valueOf(int value) {
			switch (value) {
			case 0: return AFRICA;
			case 1: return AMERICA;
			case 2: return ANTARCTICA;
			case 3: return ARCTIC;
			case 4: return ASIA;
			case 5: return ATLANTIC;
			case 6: return AUSTRALIA;
			case 7: return EUROPE;
			case 8: return INDIAN;
			case 9: return MIDEAST;
			case 10: return PACIFIC;
			default: return null;
			}
		}
		public static com.google.protobuf.Internal.EnumLiteMap<Continent>
		internalGetValueMap() {
			return internalValueMap;
		}
		private static com.google.protobuf.Internal.EnumLiteMap<Continent>
		internalValueMap =
		new com.google.protobuf.Internal.EnumLiteMap<Continent>() {
			public Continent findValueByNumber(int number) {
				return Continent.valueOf(number);
			}
		};
		public final com.google.protobuf.Descriptors.EnumValueDescriptor getValueDescriptor() {
			return getDescriptor().getValues().get(index);
		}
		public final com.google.protobuf.Descriptors.EnumDescriptor
		getDescriptorForType() {
			return getDescriptor();
		}
		public static final com.google.protobuf.Descriptors.EnumDescriptor
		getDescriptor() {
			return WorldClockProtocol.getDescriptor().getEnumTypes().get(0);
		}
		private static final Continent[] VALUES = values();
		public static Continent valueOf(
				com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
			if (desc.getType() != getDescriptor()) {
				throw new java.lang.IllegalArgumentException(
						"EnumValueDescriptor is not for this type.");
			}
			return VALUES[desc.getIndex()];
		}
		private final int index;
		private final int value;
		private Continent(int index, int value) {
			this.index = index;
			this.value = value;
		}
		// @@protoc_insertion_point(enum_scope:Continent)
	}

	public enum DayOfWeek implements com.google.protobuf.ProtocolMessageEnum {
		/**
		 * <code>SUNDAY = 1;</code>
		 */
		SUNDAY(0, 1),
		/**
		 * <code>MONDAY = 2;</code>
		 */
		MONDAY(1, 2),
		/**
		 * <code>TUESDAY = 3;</code>
		 */
		TUESDAY(2, 3),
		/**
		 * <code>WEDNESDAY = 4;</code>
		 */
		WEDNESDAY(3, 4),
		/**
		 * <code>THURSDAY = 5;</code>
		 */
		THURSDAY(4, 5),
		/**
		 * <code>FRIDAY = 6;</code>
		 */
		FRIDAY(5, 6),
		/**
		 * <code>SATURDAY = 7;</code>
		 */
		SATURDAY(6, 7),
		;
		/**
		 * <code>SUNDAY = 1;</code>
		 */
		public static final int SUNDAY_VALUE = 1;
		/**
		 * <code>MONDAY = 2;</code>
		 */
		public static final int MONDAY_VALUE = 2;
		/**
		 * <code>TUESDAY = 3;</code>
		 */
		public static final int TUESDAY_VALUE = 3;
		/**
		 * <code>WEDNESDAY = 4;</code>
		 */
		public static final int WEDNESDAY_VALUE = 4;
		/**
		 * <code>THURSDAY = 5;</code>
		 */
		public static final int THURSDAY_VALUE = 5;
		/**
		 * <code>FRIDAY = 6;</code>
		 */
		public static final int FRIDAY_VALUE = 6;
		/**
		 * <code>SATURDAY = 7;</code>
		 */
		public static final int SATURDAY_VALUE = 7;
		public final int getNumber() { return value; }
		public static DayOfWeek valueOf(int value) {
			switch (value) {
			case 1: return SUNDAY;
			case 2: return MONDAY;
			case 3: return TUESDAY;
			case 4: return WEDNESDAY;
			case 5: return THURSDAY;
			case 6: return FRIDAY;
			case 7: return SATURDAY;
			default: return null;
			}
		}
		public static com.google.protobuf.Internal.EnumLiteMap<DayOfWeek>
		internalGetValueMap() {
			return internalValueMap;
		}
		private static com.google.protobuf.Internal.EnumLiteMap<DayOfWeek>
		internalValueMap =
		new com.google.protobuf.Internal.EnumLiteMap<DayOfWeek>() {
			public DayOfWeek findValueByNumber(int number) {
				return DayOfWeek.valueOf(number);
			}
		};
		public final com.google.protobuf.Descriptors.EnumValueDescriptor
		getValueDescriptor() {
			return getDescriptor().getValues().get(index);
		}
		public final com.google.protobuf.Descriptors.EnumDescriptor
		getDescriptorForType() {
			return getDescriptor();
		}
		public static final com.google.protobuf.Descriptors.EnumDescriptor
		getDescriptor() {
			return WorldClockProtocol.getDescriptor().getEnumTypes().get(1);
		}
		private static final DayOfWeek[] VALUES = values();
		public static DayOfWeek valueOf(
				com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
			if (desc.getType() != getDescriptor()) {
				throw new java.lang.IllegalArgumentException(
						"EnumValueDescriptor is not for this type.");
			}
			return VALUES[desc.getIndex()];
		}
		private final int index;
		private final int value;
		private DayOfWeek(int index, int value) {
			this.index = index;
			this.value = value;
		}
		// @@protoc_insertion_point(enum_scope:DayOfWeek)
	}
	public interface LocationOrBuilder
	extends com.google.protobuf.MessageOrBuilder {
		// required .Continent continent = 1;
		/**
		 * <code>required .Continent continent = 1;</code>
		 */
		boolean hasContinent();
		/**
		 * <code>required .Continent continent = 1;</code>
		 */
		WorldClockProtocol.Continent getContinent();
		// required string city = 2;
		/**
		 * <code>required string city = 2;</code>
		 */
		boolean hasCity();
		/**
		 * <code>required string city = 2;</code>
		 */
		java.lang.String getCity();
		/**
		 * <code>required string city = 2;</code>
		 */
		com.google.protobuf.ByteString
		getCityBytes();
	}
	/**
	 * Protobuf type {@code Location}
	 */
	public static final class Location extends
	com.google.protobuf.GeneratedMessage
	implements LocationOrBuilder {
		// Use Location.newBuilder() to construct.
		private Location(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
			super(builder);
			this.unknownFields = builder.getUnknownFields();
		}
		private Location(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }
		private static final Location defaultInstance;
		public static Location getDefaultInstance() {
			return defaultInstance;
		}
		public Location getDefaultInstanceForType() {
			return defaultInstance;
		}
		private final com.google.protobuf.UnknownFieldSet unknownFields;
		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet
		getUnknownFields() {
			return this.unknownFields;
		}
		private Location(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			initFields();
			int mutable_bitField0_ = 0;
			com.google.protobuf.UnknownFieldSet.Builder unknownFields =
					com.google.protobuf.UnknownFieldSet.newBuilder();
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					default: {
						if (!parseUnknownField(input, unknownFields,
								extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					case 8: {
						int rawValue = input.readEnum();
						WorldClockProtocol.Continent value = WorldClockProtocol.Continent.valueOf(rawValue);
						if (value == null) {
							unknownFields.mergeVarintField(1, rawValue);
						} else {
							bitField0_ |= 0x00000001;
							continent_ = value;
						}
						break;
					}
					case 18: {
						bitField0_ |= 0x00000002;
						city_ = input.readBytes();
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(
						e.getMessage()).setUnfinishedMessage(this);
			} finally {
				this.unknownFields = unknownFields.build();
				makeExtensionsImmutable();
			}
		}
		public static final com.google.protobuf.Descriptors.Descriptor
		getDescriptor() {
			return WorldClockProtocol.internal_static_Location_descriptor;
		}
		protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
		internalGetFieldAccessorTable() {
			return WorldClockProtocol.internal_static_Location_fieldAccessorTable
					.ensureFieldAccessorsInitialized(
							WorldClockProtocol.Location.class, WorldClockProtocol.Location.Builder.class);
		}
		public static com.google.protobuf.Parser<Location> PARSER =
				new com.google.protobuf.AbstractParser<Location>() {
			public Location parsePartialFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
							throws com.google.protobuf.InvalidProtocolBufferException {
				return new Location(input, extensionRegistry);
			}
		};
		@java.lang.Override
		public com.google.protobuf.Parser<Location> getParserForType() {
			return PARSER;
		}
		private int bitField0_;
		// required .Continent continent = 1;
		public static final int CONTINENT_FIELD_NUMBER = 1;
		private WorldClockProtocol.Continent continent_;
		/**
		 * <code>required .Continent continent = 1;</code>
		 */
		public boolean hasContinent() {
			return ((bitField0_ & 0x00000001) == 0x00000001);
		}
		/**
		 * <code>required .Continent continent = 1;</code>
		 */
		public WorldClockProtocol.Continent getContinent() {
			return continent_;
		}
		// required string city = 2;
		public static final int CITY_FIELD_NUMBER = 2;
		private java.lang.Object city_;
		/**
		 * <code>required string city = 2;</code>
		 */
		public boolean hasCity() {
			return ((bitField0_ & 0x00000002) == 0x00000002);
		}
		/**
		 * <code>required string city = 2;</code>
		 */
		public java.lang.String getCity() {
			java.lang.Object ref = city_;
			if (ref instanceof java.lang.String) {
				return (java.lang.String) ref;
			} else {
				com.google.protobuf.ByteString bs = 
						(com.google.protobuf.ByteString) ref;
				java.lang.String s = bs.toStringUtf8();
				if (bs.isValidUtf8()) {
					city_ = s;
				}
				return s;
			}
		}
		/**
		 * <code>required string city = 2;</code>
		 */
		public com.google.protobuf.ByteString
		getCityBytes() {
			java.lang.Object ref = city_;
			if (ref instanceof java.lang.String) {
				com.google.protobuf.ByteString b = 
						com.google.protobuf.ByteString.copyFromUtf8(
								(java.lang.String) ref);
				city_ = b;
				return b;
			} else {
				return (com.google.protobuf.ByteString) ref;
			}
		}
		private void initFields() {
			continent_ = WorldClockProtocol.Continent.AFRICA;
			city_ = "";
		}
		private byte memoizedIsInitialized = -1;
		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized != -1) return isInitialized == 1;
			if (!hasContinent()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasCity()) {
				memoizedIsInitialized = 0;
				return false;
			}
			memoizedIsInitialized = 1;
			return true;
		}
		public void writeTo(com.google.protobuf.CodedOutputStream output)
				throws java.io.IOException {
			getSerializedSize();
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				output.writeEnum(1, continent_.getNumber());
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				output.writeBytes(2, getCityBytes());
			}
			getUnknownFields().writeTo(output);
		}
		private int memoizedSerializedSize = -1;
		public int getSerializedSize() {
			int size = memoizedSerializedSize;
			if (size != -1) return size;
			size = 0;
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				size += com.google.protobuf.CodedOutputStream
						.computeEnumSize(1, continent_.getNumber());
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				size += com.google.protobuf.CodedOutputStream
						.computeBytesSize(2, getCityBytes());
			}
			size += getUnknownFields().getSerializedSize();
			memoizedSerializedSize = size;
			return size;
		}
		private static final long serialVersionUID = 0L;
		@java.lang.Override
		protected java.lang.Object writeReplace()
				throws java.io.ObjectStreamException {
			return super.writeReplace();
		}
		public static WorldClockProtocol.Location parseFrom(
				com.google.protobuf.ByteString data)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}
		public static WorldClockProtocol.Location parseFrom(
				com.google.protobuf.ByteString data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}
		public static WorldClockProtocol.Location parseFrom(byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}
		public static WorldClockProtocol.Location parseFrom(
				byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}
		public static WorldClockProtocol.Location parseFrom(java.io.InputStream input)
				throws java.io.IOException {
			return PARSER.parseFrom(input);
		}
		public static WorldClockProtocol.Location parseFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}
		public static WorldClockProtocol.Location parseDelimitedFrom(java.io.InputStream input)
				throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input);
		}
		public static WorldClockProtocol.Location parseDelimitedFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input, extensionRegistry);
		}
		public static WorldClockProtocol.Location parseFrom(
				com.google.protobuf.CodedInputStream input)
						throws java.io.IOException {
			return PARSER.parseFrom(input);
		}
		public static WorldClockProtocol.Location parseFrom(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}
		public static Builder newBuilder() { return Builder.create(); }
		public Builder newBuilderForType() { return newBuilder(); }
		public static Builder newBuilder(WorldClockProtocol.Location prototype) {
			return newBuilder().mergeFrom(prototype);
		}
		public Builder toBuilder() { return newBuilder(this); }
		@java.lang.Override
		protected Builder newBuilderForType(
				com.google.protobuf.GeneratedMessage.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}
		/**
		 * Protobuf type {@code Location}
		 */
		public static final class Builder extends
		com.google.protobuf.GeneratedMessage.Builder<Builder>
		implements WorldClockProtocol.LocationOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor
			getDescriptor() {
				return WorldClockProtocol.internal_static_Location_descriptor;
			}
			protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
			internalGetFieldAccessorTable() {
				return WorldClockProtocol.internal_static_Location_fieldAccessorTable
						.ensureFieldAccessorsInitialized(
								WorldClockProtocol.Location.class, WorldClockProtocol.Location.Builder.class);
			}
			// Construct using WorldClockProtocol.Location.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}
			private Builder(
					com.google.protobuf.GeneratedMessage.BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}
			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
				}
			}
			private static Builder create() {
				return new Builder();
			}
			public Builder clear() {
				super.clear();
				continent_ = WorldClockProtocol.Continent.AFRICA;
				bitField0_ = (bitField0_ & ~0x00000001);
				city_ = "";
				bitField0_ = (bitField0_ & ~0x00000002);
				return this;
			}
			public Builder clone() {
				return create().mergeFrom(buildPartial());
			}
			public com.google.protobuf.Descriptors.Descriptor
			getDescriptorForType() {
				return WorldClockProtocol.internal_static_Location_descriptor;
			}
			public WorldClockProtocol.Location getDefaultInstanceForType() {
				return WorldClockProtocol.Location.getDefaultInstance();
			}
			public WorldClockProtocol.Location build() {
				WorldClockProtocol.Location result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}
			public WorldClockProtocol.Location buildPartial() {
				WorldClockProtocol.Location result = new WorldClockProtocol.Location(this);
				int from_bitField0_ = bitField0_;
				int to_bitField0_ = 0;
				if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
					to_bitField0_ |= 0x00000001;
				}
				result.continent_ = continent_;
				if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
					to_bitField0_ |= 0x00000002;
				}
				result.city_ = city_;
				result.bitField0_ = to_bitField0_;
				onBuilt();
				return result;
			}
			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof WorldClockProtocol.Location) {
					return mergeFrom((WorldClockProtocol.Location)other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}
			public Builder mergeFrom(WorldClockProtocol.Location other) {
				if (other == WorldClockProtocol.Location.getDefaultInstance()) return this;
				if (other.hasContinent()) {
					setContinent(other.getContinent());
				}
				if (other.hasCity()) {
					bitField0_ |= 0x00000002;
					city_ = other.city_;
					onChanged();
				}
				this.mergeUnknownFields(other.getUnknownFields());
				return this;
			}
			public final boolean isInitialized() {
				if (!hasContinent()) {
					return false;
				}
				if (!hasCity()) {
					return false;
				}
				return true;
			}
			public Builder mergeFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
							throws java.io.IOException {
				WorldClockProtocol.Location parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (WorldClockProtocol.Location) e.getUnfinishedMessage();
					throw e;
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}
			private int bitField0_;
			// required .Continent continent = 1;
			private WorldClockProtocol.Continent continent_ = WorldClockProtocol.Continent.AFRICA;
			/**
			 * <code>required .Continent continent = 1;</code>
			 */
			public boolean hasContinent() {
				return ((bitField0_ & 0x00000001) == 0x00000001);
			}
			/**
			 * <code>required .Continent continent = 1;</code>
			 */
			public WorldClockProtocol.Continent getContinent() {
				return continent_;
			}
			/**
			 * <code>required .Continent continent = 1;</code>
			 */
			public Builder setContinent(WorldClockProtocol.Continent value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000001;
				continent_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required .Continent continent = 1;</code>
			 */
			public Builder clearContinent() {
				bitField0_ = (bitField0_ & ~0x00000001);
				continent_ = WorldClockProtocol.Continent.AFRICA;
				onChanged();
				return this;
			}
			// required string city = 2;
			private java.lang.Object city_ = "";
			/**
			 * <code>required string city = 2;</code>
			 */
			public boolean hasCity() {
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}
			/**
			 * <code>required string city = 2;</code>
			 */
			public java.lang.String getCity() {
				java.lang.Object ref = city_;
				if (!(ref instanceof java.lang.String)) {
					java.lang.String s = ((com.google.protobuf.ByteString) ref)
							.toStringUtf8();
					city_ = s;
					return s;
				} else {
					return (java.lang.String) ref;
				}
			}
			/**
			 * <code>required string city = 2;</code>
			 */
			public com.google.protobuf.ByteString
			getCityBytes() {
				java.lang.Object ref = city_;
				if (ref instanceof String) {
					com.google.protobuf.ByteString b = 
							com.google.protobuf.ByteString.copyFromUtf8(
									(java.lang.String) ref);
					city_ = b;
					return b;
				} else {
					return (com.google.protobuf.ByteString) ref;
				}
			}
			/**
			 * <code>required string city = 2;</code>
			 */
			public Builder setCity(
					java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				city_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required string city = 2;</code>
			 */
			public Builder clearCity() {
				bitField0_ = (bitField0_ & ~0x00000002);
				city_ = getDefaultInstance().getCity();
				onChanged();
				return this;
			}
			/**
			 * <code>required string city = 2;</code>
			 */
			public Builder setCityBytes(
					com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				city_ = value;
				onChanged();
				return this;
			}
			// @@protoc_insertion_point(builder_scope:Location)
		}
		static {
			defaultInstance = new Location(true);
			defaultInstance.initFields();
		}
		// @@protoc_insertion_point(class_scope:Location)
	}
	public interface LocationsOrBuilder
	extends com.google.protobuf.MessageOrBuilder {
		// repeated .Location location = 1;
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		java.util.List<WorldClockProtocol.Location> 
		getLocationList();
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		WorldClockProtocol.Location getLocation(int index);
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		int getLocationCount();
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		java.util.List<? extends WorldClockProtocol.LocationOrBuilder> 
		getLocationOrBuilderList();
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		WorldClockProtocol.LocationOrBuilder getLocationOrBuilder(
				int index);
	}
	/**
	 * Protobuf type {@code Locations}
	 */
	public static final class Locations extends
	com.google.protobuf.GeneratedMessage
	implements LocationsOrBuilder {
		// Use Locations.newBuilder() to construct.
		private Locations(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
			super(builder);
			this.unknownFields = builder.getUnknownFields();
		}
		private Locations(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }
		private static final Locations defaultInstance;
		public static Locations getDefaultInstance() {
			return defaultInstance;
		}
		public Locations getDefaultInstanceForType() {
			return defaultInstance;
		}
		private final com.google.protobuf.UnknownFieldSet unknownFields;
		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet
		getUnknownFields() {
			return this.unknownFields;
		}
		private Locations(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			initFields();
			int mutable_bitField0_ = 0;
			com.google.protobuf.UnknownFieldSet.Builder unknownFields =
					com.google.protobuf.UnknownFieldSet.newBuilder();
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					default: {
						if (!parseUnknownField(input, unknownFields,
								extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					case 10: {
						if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
							location_ = new java.util.ArrayList<WorldClockProtocol.Location>();
							mutable_bitField0_ |= 0x00000001;
						}
						location_.add(input.readMessage(WorldClockProtocol.Location.PARSER, extensionRegistry));
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(
						e.getMessage()).setUnfinishedMessage(this);
			} finally {
				if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
					location_ = java.util.Collections.unmodifiableList(location_);
				}
				this.unknownFields = unknownFields.build();
				makeExtensionsImmutable();
			}
		}
		public static final com.google.protobuf.Descriptors.Descriptor
		getDescriptor() {
			return WorldClockProtocol.internal_static_Locations_descriptor;
		}
		protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
		internalGetFieldAccessorTable() {
			return WorldClockProtocol.internal_static_Locations_fieldAccessorTable
					.ensureFieldAccessorsInitialized(
							WorldClockProtocol.Locations.class, WorldClockProtocol.Locations.Builder.class);
		}
		public static com.google.protobuf.Parser<Locations> PARSER =
				new com.google.protobuf.AbstractParser<Locations>() {
			public Locations parsePartialFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
							throws com.google.protobuf.InvalidProtocolBufferException {
				return new Locations(input, extensionRegistry);
			}
		};
		@java.lang.Override
		public com.google.protobuf.Parser<Locations> getParserForType() {
			return PARSER;
		}
		// repeated .Location location = 1;
		public static final int LOCATION_FIELD_NUMBER = 1;
		private java.util.List<WorldClockProtocol.Location> location_;
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		public java.util.List<WorldClockProtocol.Location> getLocationList() {
			return location_;
		}
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		public java.util.List<? extends WorldClockProtocol.LocationOrBuilder> 
		getLocationOrBuilderList() {
			return location_;
		}
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		public int getLocationCount() {
			return location_.size();
		}
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		public WorldClockProtocol.Location getLocation(int index) {
			return location_.get(index);
		}
		/**
		 * <code>repeated .Location location = 1;</code>
		 */
		public WorldClockProtocol.LocationOrBuilder getLocationOrBuilder(
				int index) {
			return location_.get(index);
		}
		private void initFields() {
			location_ = java.util.Collections.emptyList();
		}
		private byte memoizedIsInitialized = -1;
		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized != -1) return isInitialized == 1;
			for (int i = 0; i < getLocationCount(); i++) {
				if (!getLocation(i).isInitialized()) {
					memoizedIsInitialized = 0;
					return false;
				}
			}
			memoizedIsInitialized = 1;
			return true;
		}
		public void writeTo(com.google.protobuf.CodedOutputStream output)
				throws java.io.IOException {
			getSerializedSize();
			for (int i = 0; i < location_.size(); i++) {
				output.writeMessage(1, location_.get(i));
			}
			getUnknownFields().writeTo(output);
		}
		private int memoizedSerializedSize = -1;
		public int getSerializedSize() {
			int size = memoizedSerializedSize;
			if (size != -1) return size;
			size = 0;
			for (int i = 0; i < location_.size(); i++) {
				size += com.google.protobuf.CodedOutputStream
						.computeMessageSize(1, location_.get(i));
			}
			size += getUnknownFields().getSerializedSize();
			memoizedSerializedSize = size;
			return size;
		}
		private static final long serialVersionUID = 0L;
		@java.lang.Override
		protected java.lang.Object writeReplace()
				throws java.io.ObjectStreamException {
			return super.writeReplace();
		}
		public static WorldClockProtocol.Locations parseFrom(
				com.google.protobuf.ByteString data)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}
		public static WorldClockProtocol.Locations parseFrom(
				com.google.protobuf.ByteString data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}
		public static WorldClockProtocol.Locations parseFrom(byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}
		public static WorldClockProtocol.Locations parseFrom(
				byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}
		public static WorldClockProtocol.Locations parseFrom(java.io.InputStream input)
				throws java.io.IOException {
			return PARSER.parseFrom(input);
		}
		public static WorldClockProtocol.Locations parseFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}
		public static WorldClockProtocol.Locations parseDelimitedFrom(java.io.InputStream input)
				throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input);
		}
		public static WorldClockProtocol.Locations parseDelimitedFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input, extensionRegistry);
		}
		public static WorldClockProtocol.Locations parseFrom(
				com.google.protobuf.CodedInputStream input)
						throws java.io.IOException {
			return PARSER.parseFrom(input);
		}
		public static WorldClockProtocol.Locations parseFrom(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}
		public static Builder newBuilder() { return Builder.create(); }
		public Builder newBuilderForType() { return newBuilder(); }
		public static Builder newBuilder(WorldClockProtocol.Locations prototype) {
			return newBuilder().mergeFrom(prototype);
		}
		public Builder toBuilder() { return newBuilder(this); }
		@java.lang.Override
		protected Builder newBuilderForType(
				com.google.protobuf.GeneratedMessage.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}
		/**
		 * Protobuf type {@code Locations}
		 */
		public static final class Builder extends
		com.google.protobuf.GeneratedMessage.Builder<Builder>
		implements WorldClockProtocol.LocationsOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor
			getDescriptor() {
				return WorldClockProtocol.internal_static_Locations_descriptor;
			}
			protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
			internalGetFieldAccessorTable() {
				return WorldClockProtocol.internal_static_Locations_fieldAccessorTable
						.ensureFieldAccessorsInitialized(
								WorldClockProtocol.Locations.class, WorldClockProtocol.Locations.Builder.class);
			}
			// Construct using WorldClockProtocol.Locations.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}
			private Builder(
					com.google.protobuf.GeneratedMessage.BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}
			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
					getLocationFieldBuilder();
				}
			}
			private static Builder create() {
				return new Builder();
			}
			public Builder clear() {
				super.clear();
				if (locationBuilder_ == null) {
					location_ = java.util.Collections.emptyList();
					bitField0_ = (bitField0_ & ~0x00000001);
				} else {
					locationBuilder_.clear();
				}
				return this;
			}
			public Builder clone() {
				return create().mergeFrom(buildPartial());
			}
			public com.google.protobuf.Descriptors.Descriptor
			getDescriptorForType() {
				return WorldClockProtocol.internal_static_Locations_descriptor;
			}
			public WorldClockProtocol.Locations getDefaultInstanceForType() {
				return WorldClockProtocol.Locations.getDefaultInstance();
			}
			public WorldClockProtocol.Locations build() {
				WorldClockProtocol.Locations result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}
			public WorldClockProtocol.Locations buildPartial() {
				WorldClockProtocol.Locations result = new WorldClockProtocol.Locations(this);
				int from_bitField0_ = bitField0_;
				if (locationBuilder_ == null) {
					if (((bitField0_ & 0x00000001) == 0x00000001)) {
						location_ = java.util.Collections.unmodifiableList(location_);
						bitField0_ = (bitField0_ & ~0x00000001);
					}
					result.location_ = location_;
				} else {
					result.location_ = locationBuilder_.build();
				}
				onBuilt();
				return result;
			}
			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof WorldClockProtocol.Locations) {
					return mergeFrom((WorldClockProtocol.Locations)other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}
			public Builder mergeFrom(WorldClockProtocol.Locations other) {
				if (other == WorldClockProtocol.Locations.getDefaultInstance()) return this;
				if (locationBuilder_ == null) {
					if (!other.location_.isEmpty()) {
						if (location_.isEmpty()) {
							location_ = other.location_;
							bitField0_ = (bitField0_ & ~0x00000001);
						} else {
							ensureLocationIsMutable();
							location_.addAll(other.location_);
						}
						onChanged();
					}
				} else {
					if (!other.location_.isEmpty()) {
						if (locationBuilder_.isEmpty()) {
							locationBuilder_.dispose();
							locationBuilder_ = null;
							location_ = other.location_;
							bitField0_ = (bitField0_ & ~0x00000001);
							locationBuilder_ = 
									com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
											getLocationFieldBuilder() : null;
						} else {
							locationBuilder_.addAllMessages(other.location_);
						}
					}
				}
				this.mergeUnknownFields(other.getUnknownFields());
				return this;
			}
			public final boolean isInitialized() {
				for (int i = 0; i < getLocationCount(); i++) {
					if (!getLocation(i).isInitialized()) {
						return false;
					}
				}
				return true;
			}
			public Builder mergeFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
							throws java.io.IOException {
				WorldClockProtocol.Locations parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (WorldClockProtocol.Locations) e.getUnfinishedMessage();
					throw e;
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}
			private int bitField0_;
			// repeated .Location location = 1;
			private java.util.List<WorldClockProtocol.Location> location_ =
					java.util.Collections.emptyList();
			private void ensureLocationIsMutable() {
				if (!((bitField0_ & 0x00000001) == 0x00000001)) {
					location_ = new java.util.ArrayList<WorldClockProtocol.Location>(location_);
					bitField0_ |= 0x00000001;
				}
			}
			private com.google.protobuf.RepeatedFieldBuilder<
			WorldClockProtocol.Location, WorldClockProtocol.Location.Builder, WorldClockProtocol.LocationOrBuilder> locationBuilder_;
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public java.util.List<WorldClockProtocol.Location> getLocationList() {
				if (locationBuilder_ == null) {
					return java.util.Collections.unmodifiableList(location_);
				} else {
					return locationBuilder_.getMessageList();
				}
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public int getLocationCount() {
				if (locationBuilder_ == null) {
					return location_.size();
				} else {
					return locationBuilder_.getCount();
				}
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public WorldClockProtocol.Location getLocation(int index) {
				if (locationBuilder_ == null) {
					return location_.get(index);
				} else {
					return locationBuilder_.getMessage(index);
				}
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder setLocation(
					int index, WorldClockProtocol.Location value) {
				if (locationBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureLocationIsMutable();
					location_.set(index, value);
					onChanged();
				} else {
					locationBuilder_.setMessage(index, value);
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder setLocation(
					int index, WorldClockProtocol.Location.Builder builderForValue) {
				if (locationBuilder_ == null) {
					ensureLocationIsMutable();
					location_.set(index, builderForValue.build());
					onChanged();
				} else {
					locationBuilder_.setMessage(index, builderForValue.build());
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder addLocation(WorldClockProtocol.Location value) {
				if (locationBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureLocationIsMutable();
					location_.add(value);
					onChanged();
				} else {
					locationBuilder_.addMessage(value);
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder addLocation(
					int index, WorldClockProtocol.Location value) {
				if (locationBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureLocationIsMutable();
					location_.add(index, value);
					onChanged();
				} else {
					locationBuilder_.addMessage(index, value);
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder addLocation(
					WorldClockProtocol.Location.Builder builderForValue) {
				if (locationBuilder_ == null) {
					ensureLocationIsMutable();
					location_.add(builderForValue.build());
					onChanged();
				} else {
					locationBuilder_.addMessage(builderForValue.build());
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder addLocation(
					int index, WorldClockProtocol.Location.Builder builderForValue) {
				if (locationBuilder_ == null) {
					ensureLocationIsMutable();
					location_.add(index, builderForValue.build());
					onChanged();
				} else {
					locationBuilder_.addMessage(index, builderForValue.build());
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder addAllLocation(
					java.lang.Iterable<? extends WorldClockProtocol.Location> values) {
				if (locationBuilder_ == null) {
					ensureLocationIsMutable();
					super.addAll(values, location_);
					onChanged();
				} else {
					locationBuilder_.addAllMessages(values);
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder clearLocation() {
				if (locationBuilder_ == null) {
					location_ = java.util.Collections.emptyList();
					bitField0_ = (bitField0_ & ~0x00000001);
					onChanged();
				} else {
					locationBuilder_.clear();
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public Builder removeLocation(int index) {
				if (locationBuilder_ == null) {
					ensureLocationIsMutable();
					location_.remove(index);
					onChanged();
				} else {
					locationBuilder_.remove(index);
				}
				return this;
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public WorldClockProtocol.Location.Builder getLocationBuilder(
					int index) {
				return getLocationFieldBuilder().getBuilder(index);
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public WorldClockProtocol.LocationOrBuilder getLocationOrBuilder(
					int index) {
				if (locationBuilder_ == null) {
					return location_.get(index);  } else {
						return locationBuilder_.getMessageOrBuilder(index);
					}
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public java.util.List<? extends WorldClockProtocol.LocationOrBuilder> 
			getLocationOrBuilderList() {
				if (locationBuilder_ != null) {
					return locationBuilder_.getMessageOrBuilderList();
				} else {
					return java.util.Collections.unmodifiableList(location_);
				}
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public WorldClockProtocol.Location.Builder addLocationBuilder() {
				return getLocationFieldBuilder().addBuilder(
						WorldClockProtocol.Location.getDefaultInstance());
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public WorldClockProtocol.Location.Builder addLocationBuilder(
					int index) {
				return getLocationFieldBuilder().addBuilder(
						index, WorldClockProtocol.Location.getDefaultInstance());
			}
			/**
			 * <code>repeated .Location location = 1;</code>
			 */
			public java.util.List<WorldClockProtocol.Location.Builder> 
			getLocationBuilderList() {
				return getLocationFieldBuilder().getBuilderList();
			}
			private com.google.protobuf.RepeatedFieldBuilder<
			WorldClockProtocol.Location, WorldClockProtocol.Location.Builder, WorldClockProtocol.LocationOrBuilder> 
			getLocationFieldBuilder() {
				if (locationBuilder_ == null) {
					locationBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
							WorldClockProtocol.Location, WorldClockProtocol.Location.Builder, WorldClockProtocol.LocationOrBuilder>(
									location_,
									((bitField0_ & 0x00000001) == 0x00000001),
									getParentForChildren(),
									isClean());
					location_ = null;
				}
				return locationBuilder_;
			}
			// @@protoc_insertion_point(builder_scope:Locations)
		}
		static {
			defaultInstance = new Locations(true);
			defaultInstance.initFields();
		}
		// @@protoc_insertion_point(class_scope:Locations)
	}
	
	public interface LocalTimeOrBuilder extends com.google.protobuf.MessageOrBuilder {
		// required uintyear = 1;
		/**
		 * <code>required uintyear = 1;</code>
		 */
		boolean hasYear();
		/**
		 * <code>required uintyear = 1;</code>
		 */
		int getYear();
		// required uintmonth = 2;
		/**
		 * <code>required uintmonth = 2;</code>
		 */
		boolean hasMonth();
		/**
		 * <code>required uintmonth = 2;</code>
		 */
		int getMonth();
		// required uintdayOfMonth = 4;
		/**
		 * <code>required uintdayOfMonth = 4;</code>
		 */
		boolean hasDayOfMonth();
		/**
		 * <code>required uintdayOfMonth = 4;</code>
		 */
		int getDayOfMonth();
		// required .DayOfWeek dayOfWeek = 5;
		/**
		 * <code>required .DayOfWeek dayOfWeek = 5;</code>
		 */
		boolean hasDayOfWeek();
		/**
		 * <code>required .DayOfWeek dayOfWeek = 5;</code>
		 */
		WorldClockProtocol.DayOfWeek getDayOfWeek();
		// required uinthour = 6;
		/**
		 * <code>required uinthour = 6;</code>
		 */
		boolean hasHour();
		/**
		 * <code>required uinthour = 6;</code>
		 */
		int getHour();
		// required uintminute = 7;
		/**
		 * <code>required uintminute = 7;</code>
		 */
		boolean hasMinute();
		/**
		 * <code>required uintminute = 7;</code>
		 */
		int getMinute();
		// required uintsecond = 8;
		/**
		 * <code>required uintsecond = 8;</code>
		 */
		boolean hasSecond();
		/**
		 * <code>required uintsecond = 8;</code>
		 */
		int getSecond();
	}
	
	/**
	 * Protobuf type {@code LocalTime}
	 */
	public static final class LocalTime extends com.google.protobuf.GeneratedMessage implements LocalTimeOrBuilder {
		// Use LocalTime.newBuilder() to construct.
		private LocalTime(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
			super(builder);
			this.unknownFields = builder.getUnknownFields();
		}
		private LocalTime(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }
		private static final LocalTime defaultInstance;
		public static LocalTime getDefaultInstance() {
			return defaultInstance;
		}
		public LocalTime getDefaultInstanceForType() {
			return defaultInstance;
		}
		private final com.google.protobuf.UnknownFieldSet unknownFields;
		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet
		getUnknownFields() {
			return this.unknownFields;
		}
		private LocalTime(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			initFields();
			int mutable_bitField0_ = 0;
			com.google.protobuf.UnknownFieldSet.Builder unknownFields =
					com.google.protobuf.UnknownFieldSet.newBuilder();
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					default: {
						if (!parseUnknownField(input, unknownFields,
								extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					case 8: {
						bitField0_ |= 0x00000001;
						year_ = input.readUInt32();
						break;
					}
					case 16: {
						bitField0_ |= 0x00000002;
						month_ = input.readUInt32();
						break;
					}
					case 32: {
						bitField0_ |= 0x00000004;
						dayOfMonth_ = input.readUInt32();
						break;
					}
					case 40: {
						int rawValue = input.readEnum();
						WorldClockProtocol.DayOfWeek value = WorldClockProtocol.DayOfWeek.valueOf(rawValue);
						if (value == null) {
							unknownFields.mergeVarintField(5, rawValue);
						} else {
							bitField0_ |= 0x00000008;
							dayOfWeek_ = value;
						}
						break;
					}
					case 48: {
						bitField0_ |= 0x00000010;
						hour_ = input.readUInt32();
						break;
					}
					case 56: {
						bitField0_ |= 0x00000020;
						minute_ = input.readUInt32();
						break;
					}
					case 64: {
						bitField0_ |= 0x00000040;
						second_ = input.readUInt32();
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(
						e.getMessage()).setUnfinishedMessage(this);
			} finally {
				this.unknownFields = unknownFields.build();
				makeExtensionsImmutable();
			}
		}
		public static final com.google.protobuf.Descriptors.Descriptor
		getDescriptor() {
			return WorldClockProtocol.internal_static_LocalTime_descriptor;
		}
		protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
		internalGetFieldAccessorTable() {
			return WorldClockProtocol.internal_static_LocalTime_fieldAccessorTable
					.ensureFieldAccessorsInitialized(
							WorldClockProtocol.LocalTime.class, WorldClockProtocol.LocalTime.Builder.class);
		}
		public static com.google.protobuf.Parser<LocalTime> PARSER =
				new com.google.protobuf.AbstractParser<LocalTime>() {
			public LocalTime parsePartialFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
							throws com.google.protobuf.InvalidProtocolBufferException {
				return new LocalTime(input, extensionRegistry);
			}
		};
		@java.lang.Override
		public com.google.protobuf.Parser<LocalTime> getParserForType() {
			return PARSER;
		}
		private int bitField0_;
		// required uintyear = 1;
		public static final int YEAR_FIELD_NUMBER = 1;
		private int year_;
		/**
		 * <code>required uintyear = 1;</code>
		 */
		public boolean hasYear() {
			return ((bitField0_ & 0x00000001) == 0x00000001);
		}
		/**
		 * <code>required uintyear = 1;</code>
		 */
		public int getYear() {
			return year_;
		}
		// required uintmonth = 2;
		public static final int MONTH_FIELD_NUMBER = 2;
		private int month_;
		/**
		 * <code>required uintmonth = 2;</code>
		 */
		public boolean hasMonth() {
			return ((bitField0_ & 0x00000002) == 0x00000002);
		}
		/**
		 * <code>required uintmonth = 2;</code>
		 */
		public int getMonth() {
			return month_;
		}
		// required uintdayOfMonth = 4;
		public static final int DAYOFMONTH_FIELD_NUMBER = 4;
		private int dayOfMonth_;
		/**
		 * <code>required uintdayOfMonth = 4;</code>
		 */
		public boolean hasDayOfMonth() {
			return ((bitField0_ & 0x00000004) == 0x00000004);
		}
		/**
		 * <code>required uintdayOfMonth = 4;</code>
		 */
		public int getDayOfMonth() {
			return dayOfMonth_;
		}
		// required .DayOfWeek dayOfWeek = 5;
		public static final int DAYOFWEEK_FIELD_NUMBER = 5;
		private WorldClockProtocol.DayOfWeek dayOfWeek_;
		/**
		 * <code>required .DayOfWeek dayOfWeek = 5;</code>
		 */
		public boolean hasDayOfWeek() {
			return ((bitField0_ & 0x00000008) == 0x00000008);
		}
		/**
		 * <code>required .DayOfWeek dayOfWeek = 5;</code>
		 */
		public WorldClockProtocol.DayOfWeek getDayOfWeek() {
			return dayOfWeek_;
		}
		// required uinthour = 6;
		public static final int HOUR_FIELD_NUMBER = 6;
		private int hour_;
		/**
		 * <code>required uinthour = 6;</code>
		 */
		public boolean hasHour() {
			return ((bitField0_ & 0x00000010) == 0x00000010);
		}
		/**
		 * <code>required uinthour = 6;</code>
		 */
		public int getHour() {
			return hour_;
		}
		// required uintminute = 7;
		public static final int MINUTE_FIELD_NUMBER = 7;
		private int minute_;
		/**
		 * <code>required uintminute = 7;</code>
		 */
		public boolean hasMinute() {
			return ((bitField0_ & 0x00000020) == 0x00000020);
		}
		/**
		 * <code>required uintminute = 7;</code>
		 */
		public int getMinute() {
			return minute_;
		}
		// required uintsecond = 8;
		public static final int SECOND_FIELD_NUMBER = 8;
		private int second_;
		/**
		 * <code>required uintsecond = 8;</code>
		 */
		public boolean hasSecond() {
			return ((bitField0_ & 0x00000040) == 0x00000040);
		}
		/**
		 * <code>required uintsecond = 8;</code>
		 */
		public int getSecond() {
			return second_;
		}
		private void initFields() {
			year_ = 0;
			month_ = 0;
			dayOfMonth_ = 0;
			dayOfWeek_ = WorldClockProtocol.DayOfWeek.SUNDAY;
			hour_ = 0;
			minute_ = 0;
			second_ = 0;
		}
		private byte memoizedIsInitialized = -1;
		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized != -1) return isInitialized == 1;
			if (!hasYear()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasMonth()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasDayOfMonth()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasDayOfWeek()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasHour()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasMinute()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasSecond()) {
				memoizedIsInitialized = 0;
				return false;
			}
			memoizedIsInitialized = 1;
			return true;
		}
		public void writeTo(com.google.protobuf.CodedOutputStream output)
				throws java.io.IOException {
			getSerializedSize();
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				output.writeUInt32(1, year_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				output.writeUInt32(2, month_);
			}
			if (((bitField0_ & 0x00000004) == 0x00000004)) {
				output.writeUInt32(4, dayOfMonth_);
			}
			if (((bitField0_ & 0x00000008) == 0x00000008)) {
				output.writeEnum(5, dayOfWeek_.getNumber());
			}
			if (((bitField0_ & 0x00000010) == 0x00000010)) {
				output.writeUInt32(6, hour_);
			}
			if (((bitField0_ & 0x00000020) == 0x00000020)) {
				output.writeUInt32(7, minute_);
			}
			if (((bitField0_ & 0x00000040) == 0x00000040)) {
				output.writeUInt32(8, second_);
			}
			getUnknownFields().writeTo(output);
		}
		private int memoizedSerializedSize = -1;
		public int getSerializedSize() {
			int size = memoizedSerializedSize;
			if (size != -1) return size;
			size = 0;
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				size += com.google.protobuf.CodedOutputStream
						.computeUInt32Size(1, year_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				size += com.google.protobuf.CodedOutputStream
						.computeUInt32Size(2, month_);
			}
			if (((bitField0_ & 0x00000004) == 0x00000004)) {
				size += com.google.protobuf.CodedOutputStream
						.computeUInt32Size(4, dayOfMonth_);
			}
			if (((bitField0_ & 0x00000008) == 0x00000008)) {
				size += com.google.protobuf.CodedOutputStream
						.computeEnumSize(5, dayOfWeek_.getNumber());
			}
			if (((bitField0_ & 0x00000010) == 0x00000010)) {
				size += com.google.protobuf.CodedOutputStream
						.computeUInt32Size(6, hour_);
			}
			if (((bitField0_ & 0x00000020) == 0x00000020)) {
				size += com.google.protobuf.CodedOutputStream
						.computeUInt32Size(7, minute_);
			}
			if (((bitField0_ & 0x00000040) == 0x00000040)) {
				size += com.google.protobuf.CodedOutputStream
						.computeUInt32Size(8, second_);
			}
			size += getUnknownFields().getSerializedSize();
			memoizedSerializedSize = size;
			return size;
		}
		private static final long serialVersionUID = 0L;
		@java.lang.Override
		protected java.lang.Object writeReplace()
				throws java.io.ObjectStreamException {
			return super.writeReplace();
		}
		public static WorldClockProtocol.LocalTime parseFrom(
				com.google.protobuf.ByteString data)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}
		public static WorldClockProtocol.LocalTime parseFrom(
				com.google.protobuf.ByteString data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}
		public static WorldClockProtocol.LocalTime parseFrom(byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}
		public static WorldClockProtocol.LocalTime parseFrom(
				byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}
		public static WorldClockProtocol.LocalTime parseFrom(java.io.InputStream input)
				throws java.io.IOException {
			return PARSER.parseFrom(input);
		}
		public static WorldClockProtocol.LocalTime parseFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}
		public static WorldClockProtocol.LocalTime parseDelimitedFrom(java.io.InputStream input)
				throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input);
		}
		public static WorldClockProtocol.LocalTime parseDelimitedFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input, extensionRegistry);
		}
		public static WorldClockProtocol.LocalTime parseFrom(
				com.google.protobuf.CodedInputStream input)
						throws java.io.IOException {
			return PARSER.parseFrom(input);
		}
		public static WorldClockProtocol.LocalTime parseFrom(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}
		public static Builder newBuilder() { return Builder.create(); }
		public Builder newBuilderForType() { return newBuilder(); }
		public static Builder newBuilder(WorldClockProtocol.LocalTime prototype) {
			return newBuilder().mergeFrom(prototype);
		}
		public Builder toBuilder() { return newBuilder(this); }
		@java.lang.Override
		protected Builder newBuilderForType(
				com.google.protobuf.GeneratedMessage.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}
		/**
		 * Protobuf type {@code LocalTime}
		 */
		public static final class Builder extends
		com.google.protobuf.GeneratedMessage.Builder<Builder>
		implements WorldClockProtocol.LocalTimeOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor
			getDescriptor() {
				return WorldClockProtocol.internal_static_LocalTime_descriptor;
			}
			protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
			internalGetFieldAccessorTable() {
				return WorldClockProtocol.internal_static_LocalTime_fieldAccessorTable
						.ensureFieldAccessorsInitialized(
								WorldClockProtocol.LocalTime.class, WorldClockProtocol.LocalTime.Builder.class);
			}
			// Construct using WorldClockProtocol.LocalTime.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}
			private Builder(
					com.google.protobuf.GeneratedMessage.BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}
			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
				}
			}
			private static Builder create() {
				return new Builder();
			}
			public Builder clear() {
				super.clear();
				year_ = 0;
				bitField0_ = (bitField0_ & ~0x00000001);
				month_ = 0;
				bitField0_ = (bitField0_ & ~0x00000002);
				dayOfMonth_ = 0;
				bitField0_ = (bitField0_ & ~0x00000004);
				dayOfWeek_ = WorldClockProtocol.DayOfWeek.SUNDAY;
				bitField0_ = (bitField0_ & ~0x00000008);
				hour_ = 0;
				bitField0_ = (bitField0_ & ~0x00000010);
				minute_ = 0;
				bitField0_ = (bitField0_ & ~0x00000020);
				second_ = 0;
				bitField0_ = (bitField0_ & ~0x00000040);
				return this;
			}
			public Builder clone() {
				return create().mergeFrom(buildPartial());
			}
			public com.google.protobuf.Descriptors.Descriptor
			getDescriptorForType() {
				return WorldClockProtocol.internal_static_LocalTime_descriptor;
			}
			public WorldClockProtocol.LocalTime getDefaultInstanceForType() {
				return WorldClockProtocol.LocalTime.getDefaultInstance();
			}
			public WorldClockProtocol.LocalTime build() {
				WorldClockProtocol.LocalTime result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}
			public WorldClockProtocol.LocalTime buildPartial() {
				WorldClockProtocol.LocalTime result = new WorldClockProtocol.LocalTime(this);
				int from_bitField0_ = bitField0_;
				int to_bitField0_ = 0;
				if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
					to_bitField0_ |= 0x00000001;
				}
				result.year_ = year_;
				if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
					to_bitField0_ |= 0x00000002;
				}
				result.month_ = month_;
				if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
					to_bitField0_ |= 0x00000004;
				}
				result.dayOfMonth_ = dayOfMonth_;
				if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
					to_bitField0_ |= 0x00000008;
				}
				result.dayOfWeek_ = dayOfWeek_;
				if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
					to_bitField0_ |= 0x00000010;
				}
				result.hour_ = hour_;
				if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
					to_bitField0_ |= 0x00000020;
				}
				result.minute_ = minute_;
				if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
					to_bitField0_ |= 0x00000040;
				}
				result.second_ = second_;
				result.bitField0_ = to_bitField0_;
				onBuilt();
				return result;
			}
			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof WorldClockProtocol.LocalTime) {
					return mergeFrom((WorldClockProtocol.LocalTime)other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}
			public Builder mergeFrom(WorldClockProtocol.LocalTime other) {
				if (other == WorldClockProtocol.LocalTime.getDefaultInstance()) return this;
				if (other.hasYear()) {
					setYear(other.getYear());
				}
				if (other.hasMonth()) {
					setMonth(other.getMonth());
				}
				if (other.hasDayOfMonth()) {
					setDayOfMonth(other.getDayOfMonth());
				}
				if (other.hasDayOfWeek()) {
					setDayOfWeek(other.getDayOfWeek());
				}
				if (other.hasHour()) {
					setHour(other.getHour());
				}
				if (other.hasMinute()) {
					setMinute(other.getMinute());
				}
				if (other.hasSecond()) {
					setSecond(other.getSecond());
				}
				this.mergeUnknownFields(other.getUnknownFields());
				return this;
			}
			public final boolean isInitialized() {
				if (!hasYear()) {
					return false;
				}
				if (!hasMonth()) {
					return false;
				}
				if (!hasDayOfMonth()) {
					return false;
				}
				if (!hasDayOfWeek()) {
					return false;
				}
				if (!hasHour()) {
					return false;
				}
				if (!hasMinute()) {
					return false;
				}
				if (!hasSecond()) {
					return false;
				}
				return true;
			}
			public Builder mergeFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
							throws java.io.IOException {
				WorldClockProtocol.LocalTime parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (WorldClockProtocol.LocalTime) e.getUnfinishedMessage();
					throw e;
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}
			private int bitField0_;
			// required uintyear = 1;
			private int year_ ;
			/**
			 * <code>required uintyear = 1;</code>
			 */
			public boolean hasYear() {
				return ((bitField0_ & 0x00000001) == 0x00000001);
			}
			/**
			 * <code>required uintyear = 1;</code>
			 */
			public int getYear() {
				return year_;
			}
			/**
			 * <code>required uintyear = 1;</code>
			 */
			public Builder setYear(int value) {
				bitField0_ |= 0x00000001;
				year_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required uintyear = 1;</code>
			 */
			public Builder clearYear() {
				bitField0_ = (bitField0_ & ~0x00000001);
				year_ = 0;
				onChanged();
				return this;
			}
			// required uintmonth = 2;
			private int month_ ;
			/**
			 * <code>required uintmonth = 2;</code>
			 */
			public boolean hasMonth() {
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}
			/**
			 * <code>required uintmonth = 2;</code>
			 */
			public int getMonth() {
				return month_;
			}
			/**
			 * <code>required uintmonth = 2;</code>
			 */
			public Builder setMonth(int value) {
				bitField0_ |= 0x00000002;
				month_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required uintmonth = 2;</code>
			 */
			public Builder clearMonth() {
				bitField0_ = (bitField0_ & ~0x00000002);
				month_ = 0;
				onChanged();
				return this;
			}
			// required uintdayOfMonth = 4;
			private int dayOfMonth_ ;
			/**
			 * <code>required uintdayOfMonth = 4;</code>
			 */
			public boolean hasDayOfMonth() {
				return ((bitField0_ & 0x00000004) == 0x00000004);
			}
			/**
			 * <code>required uintdayOfMonth = 4;</code>
			 */
			public int getDayOfMonth() {
				return dayOfMonth_;
			}
			/**
			 * <code>required uintdayOfMonth = 4;</code>
			 */
			public Builder setDayOfMonth(int value) {
				bitField0_ |= 0x00000004;
				dayOfMonth_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required uintdayOfMonth = 4;</code>
			 */
			public Builder clearDayOfMonth() {
				bitField0_ = (bitField0_ & ~0x00000004);
				dayOfMonth_ = 0;
				onChanged();
				return this;
			}
			// required .DayOfWeek dayOfWeek = 5;
			private WorldClockProtocol.DayOfWeek dayOfWeek_ = WorldClockProtocol.DayOfWeek.SUNDAY;
			/**
			 * <code>required .DayOfWeek dayOfWeek = 5;</code>
			 */
			public boolean hasDayOfWeek() {
				return ((bitField0_ & 0x00000008) == 0x00000008);
			}
			/**
			 * <code>required .DayOfWeek dayOfWeek = 5;</code>
			 */
			public WorldClockProtocol.DayOfWeek getDayOfWeek() {
				return dayOfWeek_;
			}
			/**
			 * <code>required .DayOfWeek dayOfWeek = 5;</code>
			 */
			public Builder setDayOfWeek(WorldClockProtocol.DayOfWeek value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000008;
				dayOfWeek_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required .DayOfWeek dayOfWeek = 5;</code>
			 */
			public Builder clearDayOfWeek() {
				bitField0_ = (bitField0_ & ~0x00000008);
				dayOfWeek_ = WorldClockProtocol.DayOfWeek.SUNDAY;
				onChanged();
				return this;
			}
			// required uinthour = 6;
			private int hour_ ;
			/**
			 * <code>required uinthour = 6;</code>
			 */
			public boolean hasHour() {
				return ((bitField0_ & 0x00000010) == 0x00000010);
			}
			/**
			 * <code>required uinthour = 6;</code>
			 */
			public int getHour() {
				return hour_;
			}
			/**
			 * <code>required uinthour = 6;</code>
			 */
			public Builder setHour(int value) {
				bitField0_ |= 0x00000010;
				hour_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required uinthour = 6;</code>
			 */
			public Builder clearHour() {
				bitField0_ = (bitField0_ & ~0x00000010);
				hour_ = 0;
				onChanged();
				return this;
			}
			// required uintminute = 7;
			private int minute_ ;
			/**
			 * <code>required uintminute = 7;</code>
			 */
			public boolean hasMinute() {
				return ((bitField0_ & 0x00000020) == 0x00000020);
			}
			/**
			 * <code>required uintminute = 7;</code>
			 */
			public int getMinute() {
				return minute_;
			}
			/**
			 * <code>required uintminute = 7;</code>
			 */
			public Builder setMinute(int value) {
				bitField0_ |= 0x00000020;
				minute_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required uintminute = 7;</code>
			 */
			public Builder clearMinute() {
				bitField0_ = (bitField0_ & ~0x00000020);
				minute_ = 0;
				onChanged();
				return this;
			}
			// required uintsecond = 8;
			private int second_ ;
			/**
			 * <code>required uintsecond = 8;</code>
			 */
			public boolean hasSecond() {
				return ((bitField0_ & 0x00000040) == 0x00000040);
			}
			/**
			 * <code>required uintsecond = 8;</code>
			 */
			public int getSecond() {
				return second_;
			}
			/**
			 * <code>required uintsecond = 8;</code>
			 */
			public Builder setSecond(int value) {
				bitField0_ |= 0x00000040;
				second_ = value;
				onChanged();
				return this;
			}
			/**
			 * <code>required uintsecond = 8;</code>
			 */
			public Builder clearSecond() {
				bitField0_ = (bitField0_ & ~0x00000040);
				second_ = 0;
				onChanged();
				return this;
			}
			// @@protoc_insertion_point(builder_scope:LocalTime)
		}
		static {
			defaultInstance = new LocalTime(true);
			defaultInstance.initFields();
		}
		// @@protoc_insertion_point(class_scope:LocalTime)
	}
	public interface LocalTimesOrBuilder
	extends com.google.protobuf.MessageOrBuilder {
		// repeated .LocalTime localTime = 1;
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		java.util.List<WorldClockProtocol.LocalTime> 
		getLocalTimeList();
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		WorldClockProtocol.LocalTime getLocalTime(int index);
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		int getLocalTimeCount();
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		java.util.List<? extends WorldClockProtocol.LocalTimeOrBuilder> 
		getLocalTimeOrBuilderList();
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		WorldClockProtocol.LocalTimeOrBuilder getLocalTimeOrBuilder(
				int index);
	}
	/**
	 * Protobuf type {@code LocalTimes}
	 */
	public static final class LocalTimes extends
	com.google.protobuf.GeneratedMessage
	implements LocalTimesOrBuilder {
		// Use LocalTimes.newBuilder() to construct.
		private LocalTimes(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
			super(builder);
			this.unknownFields = builder.getUnknownFields();
		}
		private LocalTimes(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }
		private static final LocalTimes defaultInstance;
		public static LocalTimes getDefaultInstance() {
			return defaultInstance;
		}
		public LocalTimes getDefaultInstanceForType() {
			return defaultInstance;
		}
		private final com.google.protobuf.UnknownFieldSet unknownFields;
		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet
		getUnknownFields() {
			return this.unknownFields;
		}
		private LocalTimes(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			initFields();
			int mutable_bitField0_ = 0;
			com.google.protobuf.UnknownFieldSet.Builder unknownFields =
					com.google.protobuf.UnknownFieldSet.newBuilder();
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					default: {
						if (!parseUnknownField(input, unknownFields,
								extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					case 10: {
						if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
							localTime_ = new java.util.ArrayList<WorldClockProtocol.LocalTime>();
							mutable_bitField0_ |= 0x00000001;
						}
						localTime_.add(input.readMessage(WorldClockProtocol.LocalTime.PARSER, extensionRegistry));
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(
						e.getMessage()).setUnfinishedMessage(this);
			} finally {
				if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
					localTime_ = java.util.Collections.unmodifiableList(localTime_);
				}
				this.unknownFields = unknownFields.build();
				makeExtensionsImmutable();
			}
		}
		public static final com.google.protobuf.Descriptors.Descriptor
		getDescriptor() {
			return WorldClockProtocol.internal_static_LocalTimes_descriptor;
		}
		protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
		internalGetFieldAccessorTable() {
			return WorldClockProtocol.internal_static_LocalTimes_fieldAccessorTable
					.ensureFieldAccessorsInitialized(
							WorldClockProtocol.LocalTimes.class, WorldClockProtocol.LocalTimes.Builder.class);
		}
		public static com.google.protobuf.Parser<LocalTimes> PARSER =
				new com.google.protobuf.AbstractParser<LocalTimes>() {
			public LocalTimes parsePartialFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
							throws com.google.protobuf.InvalidProtocolBufferException {
				return new LocalTimes(input, extensionRegistry);
			}
		};
		@java.lang.Override
		public com.google.protobuf.Parser<LocalTimes> getParserForType() {
			return PARSER;
		}
		// repeated .LocalTime localTime = 1;
		public static final int LOCALTIME_FIELD_NUMBER = 1;
		private java.util.List<WorldClockProtocol.LocalTime> localTime_;
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		public java.util.List<WorldClockProtocol.LocalTime> getLocalTimeList() {
			return localTime_;
		}
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		public java.util.List<? extends WorldClockProtocol.LocalTimeOrBuilder> 
		getLocalTimeOrBuilderList() {
			return localTime_;
		}
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		public int getLocalTimeCount() {
			return localTime_.size();
		}
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		public WorldClockProtocol.LocalTime getLocalTime(int index) {
			return localTime_.get(index);
		}
		/**
		 * <code>repeated .LocalTime localTime = 1;</code>
		 */
		public WorldClockProtocol.LocalTimeOrBuilder getLocalTimeOrBuilder(
				int index) {
			return localTime_.get(index);
		}
		private void initFields() {
			localTime_ = java.util.Collections.emptyList();
		}
		private byte memoizedIsInitialized = -1;
		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized != -1) return isInitialized == 1;
			for (int i = 0; i < getLocalTimeCount(); i++) {
				if (!getLocalTime(i).isInitialized()) {
					memoizedIsInitialized = 0;
					return false;
				}
			}
			memoizedIsInitialized = 1;
			return true;
		}
		public void writeTo(com.google.protobuf.CodedOutputStream output)
				throws java.io.IOException {
			getSerializedSize();
			for (int i = 0; i < localTime_.size(); i++) {
				output.writeMessage(1, localTime_.get(i));
			}
			getUnknownFields().writeTo(output);
		}
		private int memoizedSerializedSize = -1;
		public int getSerializedSize() {
			int size = memoizedSerializedSize;
			if (size != -1) return size;
			size = 0;
			for (int i = 0; i < localTime_.size(); i++) {
				size += com.google.protobuf.CodedOutputStream
						.computeMessageSize(1, localTime_.get(i));
			}
			size += getUnknownFields().getSerializedSize();
			memoizedSerializedSize = size;
			return size;
		}
		private static final long serialVersionUID = 0L;
		@java.lang.Override
		protected java.lang.Object writeReplace()
				throws java.io.ObjectStreamException {
			return super.writeReplace();
		}
		public static WorldClockProtocol.LocalTimes parseFrom(
				com.google.protobuf.ByteString data)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}
		public static WorldClockProtocol.LocalTimes parseFrom(
				com.google.protobuf.ByteString data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}
		public static WorldClockProtocol.LocalTimes parseFrom(byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}
		public static WorldClockProtocol.LocalTimes parseFrom(
				byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}
		public static WorldClockProtocol.LocalTimes parseFrom(java.io.InputStream input)
				throws java.io.IOException {
			return PARSER.parseFrom(input);
		}
		public static WorldClockProtocol.LocalTimes parseFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}
		public static WorldClockProtocol.LocalTimes parseDelimitedFrom(java.io.InputStream input)
				throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input);
		}
		public static WorldClockProtocol.LocalTimes parseDelimitedFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input, extensionRegistry);
		}
		public static WorldClockProtocol.LocalTimes parseFrom(
				com.google.protobuf.CodedInputStream input)
						throws java.io.IOException {
			return PARSER.parseFrom(input);
		}
		public static WorldClockProtocol.LocalTimes parseFrom(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
						throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}
		public static Builder newBuilder() { return Builder.create(); }
		public Builder newBuilderForType() { return newBuilder(); }
		public static Builder newBuilder(WorldClockProtocol.LocalTimes prototype) {
			return newBuilder().mergeFrom(prototype);
		}
		public Builder toBuilder() { return newBuilder(this); }
		@java.lang.Override
		protected Builder newBuilderForType(
				com.google.protobuf.GeneratedMessage.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}
		/**
		 * Protobuf type {@code LocalTimes}
		 */
		public static final class Builder extends
		com.google.protobuf.GeneratedMessage.Builder<Builder>
		implements WorldClockProtocol.LocalTimesOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor
			getDescriptor() {
				return WorldClockProtocol.internal_static_LocalTimes_descriptor;
			}
			protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
			internalGetFieldAccessorTable() {
				return WorldClockProtocol.internal_static_LocalTimes_fieldAccessorTable
						.ensureFieldAccessorsInitialized(
								WorldClockProtocol.LocalTimes.class, WorldClockProtocol.LocalTimes.Builder.class);
			}
			// Construct using WorldClockProtocol.LocalTimes.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}
			private Builder(
					com.google.protobuf.GeneratedMessage.BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}
			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
					getLocalTimeFieldBuilder();
				}
			}
			private static Builder create() {
				return new Builder();
			}
			public Builder clear() {
				super.clear();
				if (localTimeBuilder_ == null) {
					localTime_ = java.util.Collections.emptyList();
					bitField0_ = (bitField0_ & ~0x00000001);
				} else {
					localTimeBuilder_.clear();
				}
				return this;
			}
			public Builder clone() {
				return create().mergeFrom(buildPartial());
			}
			public com.google.protobuf.Descriptors.Descriptor
			getDescriptorForType() {
				return WorldClockProtocol.internal_static_LocalTimes_descriptor;
			}
			public WorldClockProtocol.LocalTimes getDefaultInstanceForType() {
				return WorldClockProtocol.LocalTimes.getDefaultInstance();
			}
			public WorldClockProtocol.LocalTimes build() {
				WorldClockProtocol.LocalTimes result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}
			public WorldClockProtocol.LocalTimes buildPartial() {
				WorldClockProtocol.LocalTimes result = new WorldClockProtocol.LocalTimes(this);
				int from_bitField0_ = bitField0_;
				if (localTimeBuilder_ == null) {
					if (((bitField0_ & 0x00000001) == 0x00000001)) {
						localTime_ = java.util.Collections.unmodifiableList(localTime_);
						bitField0_ = (bitField0_ & ~0x00000001);
					}
					result.localTime_ = localTime_;
				} else {
					result.localTime_ = localTimeBuilder_.build();
				}
				onBuilt();
				return result;
			}
			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof WorldClockProtocol.LocalTimes) {
					return mergeFrom((WorldClockProtocol.LocalTimes)other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}
			public Builder mergeFrom(WorldClockProtocol.LocalTimes other) {
				if (other == WorldClockProtocol.LocalTimes.getDefaultInstance()) return this;
				if (localTimeBuilder_ == null) {
					if (!other.localTime_.isEmpty()) {
						if (localTime_.isEmpty()) {
							localTime_ = other.localTime_;
							bitField0_ = (bitField0_ & ~0x00000001);
						} else {
							ensureLocalTimeIsMutable();
							localTime_.addAll(other.localTime_);
						}
						onChanged();
					}
				} else {
					if (!other.localTime_.isEmpty()) {
						if (localTimeBuilder_.isEmpty()) {
							localTimeBuilder_.dispose();
							localTimeBuilder_ = null;
							localTime_ = other.localTime_;
							bitField0_ = (bitField0_ & ~0x00000001);
							localTimeBuilder_ = 
									com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
											getLocalTimeFieldBuilder() : null;
						} else {
							localTimeBuilder_.addAllMessages(other.localTime_);
						}
					}
				}
				this.mergeUnknownFields(other.getUnknownFields());
				return this;
			}
			public final boolean isInitialized() {
				for (int i = 0; i < getLocalTimeCount(); i++) {
					if (!getLocalTime(i).isInitialized()) {
						return false;
					}
				}
				return true;
			}
			public Builder mergeFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
							throws java.io.IOException {
				WorldClockProtocol.LocalTimes parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (WorldClockProtocol.LocalTimes) e.getUnfinishedMessage();
					throw e;
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}
			private int bitField0_;
			// repeated .LocalTime localTime = 1;
			private java.util.List<WorldClockProtocol.LocalTime> localTime_ =
					java.util.Collections.emptyList();
			private void ensureLocalTimeIsMutable() {
				if (!((bitField0_ & 0x00000001) == 0x00000001)) {
					localTime_ = new java.util.ArrayList<WorldClockProtocol.LocalTime>(localTime_);
					bitField0_ |= 0x00000001;
				}
			}
			private com.google.protobuf.RepeatedFieldBuilder<
			WorldClockProtocol.LocalTime, WorldClockProtocol.LocalTime.Builder, WorldClockProtocol.LocalTimeOrBuilder> localTimeBuilder_;
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public java.util.List<WorldClockProtocol.LocalTime> getLocalTimeList() {
				if (localTimeBuilder_ == null) {
					return java.util.Collections.unmodifiableList(localTime_);
				} else {
					return localTimeBuilder_.getMessageList();
				}
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public int getLocalTimeCount() {
				if (localTimeBuilder_ == null) {
					return localTime_.size();
				} else {
					return localTimeBuilder_.getCount();
				}
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public WorldClockProtocol.LocalTime getLocalTime(int index) {
				if (localTimeBuilder_ == null) {
					return localTime_.get(index);
				} else {
					return localTimeBuilder_.getMessage(index);
				}
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder setLocalTime(
					int index, WorldClockProtocol.LocalTime value) {
				if (localTimeBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureLocalTimeIsMutable();
					localTime_.set(index, value);
					onChanged();
				} else {
					localTimeBuilder_.setMessage(index, value);
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder setLocalTime(
					int index, WorldClockProtocol.LocalTime.Builder builderForValue) {
				if (localTimeBuilder_ == null) {
					ensureLocalTimeIsMutable();
					localTime_.set(index, builderForValue.build());
					onChanged();
				} else {
					localTimeBuilder_.setMessage(index, builderForValue.build());
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder addLocalTime(WorldClockProtocol.LocalTime value) {
				if (localTimeBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureLocalTimeIsMutable();
					localTime_.add(value);
					onChanged();
				} else {
					localTimeBuilder_.addMessage(value);
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder addLocalTime(
					int index, WorldClockProtocol.LocalTime value) {
				if (localTimeBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureLocalTimeIsMutable();
					localTime_.add(index, value);
					onChanged();
				} else {
					localTimeBuilder_.addMessage(index, value);
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder addLocalTime(
					WorldClockProtocol.LocalTime.Builder builderForValue) {
				if (localTimeBuilder_ == null) {
					ensureLocalTimeIsMutable();
					localTime_.add(builderForValue.build());
					onChanged();
				} else {
					localTimeBuilder_.addMessage(builderForValue.build());
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder addLocalTime(
					int index, WorldClockProtocol.LocalTime.Builder builderForValue) {
				if (localTimeBuilder_ == null) {
					ensureLocalTimeIsMutable();
					localTime_.add(index, builderForValue.build());
					onChanged();
				} else {
					localTimeBuilder_.addMessage(index, builderForValue.build());
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder addAllLocalTime(
					java.lang.Iterable<? extends WorldClockProtocol.LocalTime> values) {
				if (localTimeBuilder_ == null) {
					ensureLocalTimeIsMutable();
					super.addAll(values, localTime_);
					onChanged();
				} else {
					localTimeBuilder_.addAllMessages(values);
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder clearLocalTime() {
				if (localTimeBuilder_ == null) {
					localTime_ = java.util.Collections.emptyList();
					bitField0_ = (bitField0_ & ~0x00000001);
					onChanged();
				} else {
					localTimeBuilder_.clear();
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public Builder removeLocalTime(int index) {
				if (localTimeBuilder_ == null) {
					ensureLocalTimeIsMutable();
					localTime_.remove(index);
					onChanged();
				} else {
					localTimeBuilder_.remove(index);
				}
				return this;
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public WorldClockProtocol.LocalTime.Builder getLocalTimeBuilder(
					int index) {
				return getLocalTimeFieldBuilder().getBuilder(index);
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public WorldClockProtocol.LocalTimeOrBuilder getLocalTimeOrBuilder(
					int index) {
				if (localTimeBuilder_ == null) {
					return localTime_.get(index);  } else {
						return localTimeBuilder_.getMessageOrBuilder(index);
					}
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public java.util.List<? extends WorldClockProtocol.LocalTimeOrBuilder> 
			getLocalTimeOrBuilderList() {
				if (localTimeBuilder_ != null) {
					return localTimeBuilder_.getMessageOrBuilderList();
				} else {
					return java.util.Collections.unmodifiableList(localTime_);
				}
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public WorldClockProtocol.LocalTime.Builder addLocalTimeBuilder() {
				return getLocalTimeFieldBuilder().addBuilder(
						WorldClockProtocol.LocalTime.getDefaultInstance());
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public WorldClockProtocol.LocalTime.Builder addLocalTimeBuilder(
					int index) {
				return getLocalTimeFieldBuilder().addBuilder(
						index, WorldClockProtocol.LocalTime.getDefaultInstance());
			}
			/**
			 * <code>repeated .LocalTime localTime = 1;</code>
			 */
			public java.util.List<WorldClockProtocol.LocalTime.Builder> 
			getLocalTimeBuilderList() {
				return getLocalTimeFieldBuilder().getBuilderList();
			}
			private com.google.protobuf.RepeatedFieldBuilder<
			WorldClockProtocol.LocalTime, WorldClockProtocol.LocalTime.Builder, WorldClockProtocol.LocalTimeOrBuilder> 
			getLocalTimeFieldBuilder() {
				if (localTimeBuilder_ == null) {
					localTimeBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
							WorldClockProtocol.LocalTime, WorldClockProtocol.LocalTime.Builder, WorldClockProtocol.LocalTimeOrBuilder>(
									localTime_,
									((bitField0_ & 0x00000001) == 0x00000001),
									getParentForChildren(),
									isClean());
					localTime_ = null;
				}
				return localTimeBuilder_;
			}
			// @@protoc_insertion_point(builder_scope:LocalTimes)
		}
		static {
			defaultInstance = new LocalTimes(true);
			defaultInstance.initFields();
		}
		// @@protoc_insertion_point(class_scope:LocalTimes)
	}
	private static com.google.protobuf.Descriptors.Descriptor
	internal_static_Location_descriptor;
	private static
	com.google.protobuf.GeneratedMessage.FieldAccessorTable
	internal_static_Location_fieldAccessorTable;
	private static com.google.protobuf.Descriptors.Descriptor
	internal_static_Locations_descriptor;
	private static
	com.google.protobuf.GeneratedMessage.FieldAccessorTable
	internal_static_Locations_fieldAccessorTable;
	private static com.google.protobuf.Descriptors.Descriptor
	internal_static_LocalTime_descriptor;
	private static
	com.google.protobuf.GeneratedMessage.FieldAccessorTable
	internal_static_LocalTime_fieldAccessorTable;
	private static com.google.protobuf.Descriptors.Descriptor
	internal_static_LocalTimes_descriptor;
	private static
	com.google.protobuf.GeneratedMessage.FieldAccessorTable
	internal_static_LocalTimes_fieldAccessorTable;
	public static com.google.protobuf.Descriptors.FileDescriptor
	getDescriptor() {
		return descriptor;
	}
	private static com.google.protobuf.Descriptors.FileDescriptor
	descriptor;
	static {
		java.lang.String[] descriptorData = {
				"\nBsrc/main/java/io/netty/example/worldcl" +
						"ock/WorldClockProtocol.proto\022\033io.netty.e" +
						"xample.worldclock\"S\n\010Location\0229\n\tcontine" +
						"nt\030\\002(\0162&.C" +
						"ontinent\022\014\n\004city\030\\002(\t\"D\n\tLocations\0227\n\010l" +
						"ocation\030\\003(\0132%.io.netty.example.worldcl" +
						"ock.Location\"\245\001\n\tLocalTime\022\014\n\004year\030\\002(\r" +
						"\022\r\n\005month\030\\002(\r\022\022\n\ndayOfMonth\030\\002(\r\0229\n\td" +
						"ayOfWeek\030\\002(\0162&.io.netty.example.worldc" +
						"lock.DayOfWeek\022\014\n\004hour\030\\002(\r\022\016\n\006minute\030\007",
						" \002(\r\022\016\n\006second\030\\002(\r\"G\n\nLocalTimes\0229\n\tlo" +
								"calTime\030\\003(\0132&.io.netty.example.worldcl" +
								"ock.LocalTime*\231\001\n\tContinent\022\n\n\006AFRICA\020\000\022" +
								"\013\n\007AMERICA\020\001\022\016\n\nANTARCTICA\020\002\022\n\n\006ARCTIC\020\003" +
								"\022\010\n\004ASIA\020\004\022\014\n\010ATLANTIC\020\005\022\r\n\tAUSTRALIA\020\006\022" +
								"\n\n\006EUROPE\020\007\022\n\n\006INDIAN\020\010\022\013\n\007MIDEAST\020\t\022\013\n\007" +
								"PACIFIC\020\n*g\n\tDayOfWeek\022\n\n\006SUNDAY\020\001\022\n\n\006MO" +
								"NDAY\020\002\022\013\n\007TUESDAY\020\003\022\r\n\tWEDNESDAY\020\004\022\014\n\010TH" +
								"URSDAY\020\005\022\n\n\006FRIDAY\020\006\022\014\n\010SATURDAY\020\007B\002H\001"
		};
		com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
				new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
			public com.google.protobuf.ExtensionRegistry assignDescriptors(
					com.google.protobuf.Descriptors.FileDescriptor root) {
				descriptor = root;
				internal_static_Location_descriptor =
						getDescriptor().getMessageTypes().get(0);
				internal_static_Location_fieldAccessorTable = new
						com.google.protobuf.GeneratedMessage.FieldAccessorTable(
								internal_static_Location_descriptor,
								new java.lang.String[] { "Continent", "City", });
				internal_static_Locations_descriptor =
						getDescriptor().getMessageTypes().get(1);
				internal_static_Locations_fieldAccessorTable = new
						com.google.protobuf.GeneratedMessage.FieldAccessorTable(
								internal_static_Locations_descriptor,
								new java.lang.String[] { "Location", });
				internal_static_LocalTime_descriptor =
						getDescriptor().getMessageTypes().get(2);
				internal_static_LocalTime_fieldAccessorTable = new
						com.google.protobuf.GeneratedMessage.FieldAccessorTable(
								internal_static_LocalTime_descriptor,
								new java.lang.String[] { "Year", "Month", "DayOfMonth", "DayOfWeek", "Hour", "Minute", "Second", });
				internal_static_LocalTimes_descriptor =
						getDescriptor().getMessageTypes().get(3);
				internal_static_LocalTimes_fieldAccessorTable = new
						com.google.protobuf.GeneratedMessage.FieldAccessorTable(
								internal_static_LocalTimes_descriptor,
								new java.lang.String[] { "LocalTime", });
				return null;
			}
		};
		com.google.protobuf.Descriptors.FileDescriptor
		.internalBuildGeneratedFileFrom(descriptorData,
				new com.google.protobuf.Descriptors.FileDescriptor[] {
		}, assigner);
	}
	// @@protoc_insertion_point(outer_class_scope)
}